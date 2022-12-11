package com.project.email.Services

import com.project.email.Domain.SentMessages
import com.project.email.Repos.SentMessagesRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress

@Service
class SendEmailService(
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.username}") private val senderEmail: String,
    @Autowired private val sentMessagesRepo: SentMessagesRepo
) {
    fun sendSimpleEmail(receiver: String, subject: String, text: String) {
        val message = SimpleMailMessage()
        message.setFrom(senderEmail)
        message.setTo(receiver)
        message.setBcc(senderEmail)
        message.setSubject(subject)
        message.setText(text)
        javaMailSender.send(message)
    }

    fun sendHtmlEmail(receiver: String, subject: String, text: String) {
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message)
        helper.setFrom(InternetAddress(senderEmail))
        helper.setTo(receiver)
        helper.setBcc(senderEmail)
        helper.setSubject(subject)
        helper.setText(text, true)
        javaMailSender.send(message)
    }

    fun addTextMessageToDb(receiver: String, subject: String, text: String): String {
        val sentMessages = SentMessages()

        sentMessages.from = senderEmail
        sentMessages.to = receiver
        sentMessages.subject = subject
        sentMessages.folder = "Sent"
        sentMessages.date = LocalDateTime.now().toString()
        sentMessages.contentType = "text/plain"
        sentMessages.flags = "Seen encrypted"
        sentMessages.content = text

        sentMessagesRepo.save(sentMessages)

        return "Saved in local database"

    }

    fun addHtmlMessageToDb(receiver: String, subject: String, text: String): String {
        val sentMessages = SentMessages()

        sentMessages.from = senderEmail
        sentMessages.to = receiver
        sentMessages.subject = subject
        sentMessages.folder = "Sent"
        sentMessages.date = LocalDateTime.now().toString()
        sentMessages.contentType = "text/html"
        sentMessages.flags = "Seen encrypted"
        sentMessages.content = "<div>$text</div>"

        sentMessagesRepo.save(sentMessages)

        return "Saved in local database"

    }

}