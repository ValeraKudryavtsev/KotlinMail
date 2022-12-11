package com.project.email.Services

import com.project.email.Repos.SentMessagesRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.mail.Flags
import javax.mail.Folder
import javax.mail.Session
import javax.mail.Store

@Service
class DeleteEmailService(
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

    fun deleteFromInbox(id: Int) {
        val inbox: Folder = store!!.getFolder("INBOX")
        inbox.open(Folder.READ_WRITE)
        val message = inbox.getMessage(id)

        message.setFlag(Flags.Flag.DELETED, true)

        inbox.close()
    }

    fun deleteFromSpam(id: Int) {
        val spam: Folder = store!!.getFolder("Spam")
        spam.open(Folder.READ_WRITE)
        val message = spam.getMessage(id)

        message.setFlag(Flags.Flag.DELETED, true)

        spam.close()
    }

    fun deleteFromSent(id: Long) {
        sentMessagesRepo.deleteById(id)
    }

    fun deleteFromTrash(id: Int) {
        val trash: Folder = store!!.getFolder("Trash")
        trash.open(Folder.READ_WRITE)
        val message = trash.getMessage(id)

        message.setFlag(Flags.Flag.DELETED, true)

        trash.close()
    }

}