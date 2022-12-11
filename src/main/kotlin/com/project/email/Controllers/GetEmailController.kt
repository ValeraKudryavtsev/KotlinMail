package com.project.email.Controllers

import com.project.email.Domain.SentMessages
import com.project.email.Services.GetEmailService
import com.project.email.MessageCount
import com.project.email.MessageInfo
import com.project.email.MessageInfoById
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/mails/get")
class GetEmailController(private val getEmailService: GetEmailService) {
    @GetMapping("/inbox")
    fun getAllEmails(): MutableList<MessageInfo> {
        return getEmailService.getAllEmails()
    }

    @GetMapping("/inbox/{id}")
    fun getEmailById(@PathVariable("id") id: Int): MessageInfoById {
        return getEmailService.getEmailById(id)
    }

    @GetMapping("/inbox/count")
    fun getEmailById(): MessageCount {
        return getEmailService.getEmailCount()
    }

    @GetMapping("/spam")
    fun getSpamFolder(): MutableList<MessageInfo> {
        return getEmailService.getSpamFolder()
    }

    @GetMapping("/trash")
    fun getTrashFolder(): MutableList<MessageInfo> {
        return getEmailService.getTrashFolder()
    }

    @GetMapping("/sent")
    fun getSentFolder(): MutableIterable<SentMessages> {
        return getEmailService.getSentFolder()
    }

    @GetMapping("/sent/{id}")
    fun getSentEmailById(@PathVariable("id") id: Long): Optional<SentMessages> {
        return getEmailService.getSentEmailById(id)
    }

    @GetMapping("/sent/count")
    fun getSentEmailCount(): Long {
        return getEmailService.getSentEmailCount()
    }

}
