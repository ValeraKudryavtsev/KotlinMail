package com.project.email.Services

import com.project.email.Domain.SentMessages
import com.project.email.MessageCount
import com.project.email.MessageInfo
import com.project.email.MessageInfoById
import com.project.email.Repos.SentMessagesRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.mail.*

@Service
class GetEmailService(
    @Value("\${spring.get-mail.username}") private val username: String,
    @Value("\${spring.get-mail.password}") private val password: String,
    @Value("\${spring.get-mail.host}") private val host: String,
    @Value("\${spring.get-mail.port}") private val port: String,
    @Value("\${spring.get-mail.protocol}") private val protocol: String,
    @Autowired private val sentMessagesRepo: SentMessagesRepo
) {
    var store: Store? = null;

    init {
        val properties = Properties()
        properties.put("mail.debug", "false")
        properties.put("mail.store.protocol", protocol)
        properties.put("mail.imap.ssl.enable", "true")
        properties.put("mail.imap.port", port)

        val session: Session = Session.getDefaultInstance(properties)
        session.setDebug(false)

        store = session.getStore()
        store!!.connect(host, username, password)
    }

    fun getAllEmails(): MutableList<MessageInfo> {
        val inbox: Folder = store!!.getFolder("INBOX")
        inbox.open(Folder.READ_ONLY)
        val messages = inbox.messages
        val messagesList: MutableList<MessageInfo> = addMessagesToDataClassList(messages)

        inbox.close()

        return messagesList
    }

    fun getEmailById(id: Int): MessageInfoById {
        val inbox: Folder = store!!.getFolder("INBOX")
        inbox.open(Folder.READ_ONLY)
        val message = inbox.getMessage(id)

        val messageInfoById = addMessageToDataClass(message)

        inbox.close()

        return messageInfoById
    }

    fun getEmailCount(): MessageCount {
        val inbox: Folder = store!!.getFolder("INBOX")
        inbox.open(Folder.READ_ONLY)

        val messageCount = MessageCount(
            count = inbox.messageCount
        )

        inbox.close()

        return messageCount
    }

    fun getSpamFolder(): MutableList<MessageInfo> {
        val spam: Folder = store!!.getFolder("Spam")
        spam.open(Folder.READ_ONLY)
        val messages = spam.messages
        val messagesList: MutableList<MessageInfo> = addMessagesToDataClassList(messages)

        spam.close()

        return messagesList
    }

    fun getTrashFolder(): MutableList<MessageInfo> {
        val trash: Folder = store!!.getFolder("Trash")
        trash.open(Folder.READ_ONLY)
        val messages = trash.messages
        val messagesList: MutableList<MessageInfo> = addMessagesToDataClassList(messages)

        trash.close()

        return messagesList
    }

    fun getSentFolder(): MutableIterable<SentMessages> {
        return sentMessagesRepo.findAll()
    }

    fun getSentEmailById(id: Long): Optional<SentMessages> {
        return sentMessagesRepo.findById(id)
    }

    fun getSentEmailCount(): Long {
        return sentMessagesRepo.count()
    }

    fun addMessageToDataClass(message: Message): MessageInfoById {
        return MessageInfoById(
            id = message.messageNumber,
            from = formatEmailAddress(message),
            to = message.allRecipients[0].toString(),
            subject = message.subject ?: "Empty",
            folder = message.folder.toString(),
            date = message.receivedDate,
            contentType = message.contentType.substringBefore(';'),
            flags = message.flags.toString(),
            content = message.content.toString()
        )
    }

    fun addMessagesToDataClassList(messages: Array<Message>): MutableList<MessageInfo> {
        val messagesList: MutableList<MessageInfo> = mutableListOf()

        for(message in messages) {
            val messageInfo = MessageInfo(
                id = message.messageNumber,
                from = formatEmailAddress(message),
                to = message.allRecipients[0].toString(),
                subject = message.subject ?: "Empty",
                folder = message.folder.toString(),
                date = message.receivedDate,
                contentType = message.contentType.substringBefore(';'),
                flags = message.flags.toString(),
            )
            messagesList.add(messageInfo)
        }

        return messagesList
    }

    fun formatEmailAddress(message: Message): String {
        var from = message.from[0].toString()

        if (from.contains("<") or from.contains(">")) {
            from = from.substringAfter('<')
            from = from.substringBefore('>')
        }

        return from
    }

}