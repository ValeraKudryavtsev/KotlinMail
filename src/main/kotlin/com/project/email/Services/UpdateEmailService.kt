package com.project.email.Services

import com.project.email.Repos.SentMessagesRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.mail.*

@Service
class UpdateEmailService(
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

    fun setInboxEmailSeen(id: Int) {
        val inbox: Folder = store!!.getFolder("INBOX")
        inbox.open(Folder.READ_WRITE)
        val message = inbox.getMessage(id)

        message.setFlag(Flags.Flag.SEEN, true)

        inbox.close()
    }

    fun setInboxEmailUnseen(id: Int) {
        val inbox: Folder = store!!.getFolder("INBOX")
        inbox.open(Folder.READ_WRITE)
        val message = inbox.getMessage(id)

        message.setFlag(Flags.Flag.SEEN, false)

        inbox.close()
    }

    fun setInboxEmailFlagged(id: Int) {
        val inbox: Folder = store!!.getFolder("INBOX")
        inbox.open(Folder.READ_WRITE)
        val message = inbox.getMessage(id)

        message.setFlag(Flags.Flag.FLAGGED, true)

        inbox.close()
    }

    fun setInboxEmailUnflagged(id: Int) {
        val inbox: Folder = store!!.getFolder("INBOX")
        inbox.open(Folder.READ_WRITE)
        val message = inbox.getMessage(id)

        message.setFlag(Flags.Flag.FLAGGED, false)

        inbox.close()
    }

    fun setSentEmailFlagged(id: Long) {
        val updatedMessage = sentMessagesRepo.findById(id).get()

        updatedMessage.flags = updatedMessage.flags.plus(" Flugged")

        sentMessagesRepo.save(updatedMessage)
    }

    fun setSentEmailUnflagged(id: Long) {
        val updatedMessage = sentMessagesRepo.findById(id).get()

        updatedMessage.flags = updatedMessage.flags.replace(" Flugged", "")

        sentMessagesRepo.save(updatedMessage)
    }

    fun moveInboxEmailToTrash(id: Int) {
        val inbox: Folder = store!!.getFolder("INBOX")
        inbox.open(Folder.READ_WRITE)
        val trash: Folder = store!!.getFolder("Trash")
        trash.open(Folder.READ_WRITE)

        val msg = arrayOf<Message>(inbox.getMessage(id))

        trash.appendMessages(msg)
        msg[0].setFlag(Flags.Flag.DELETED, true)

        inbox.close()
        trash.close()
    }

    fun moveSentEmailToTrash(id: Long) {
        val updatedMessage = sentMessagesRepo.findById(id).get()

        updatedMessage.folder = "Sent Trash"

        sentMessagesRepo.save(updatedMessage)
    }

}