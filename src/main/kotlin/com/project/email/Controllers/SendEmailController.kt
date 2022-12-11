package com.project.email.Controllers

import com.project.email.HTMLBodyResponse
import com.project.email.Services.SendEmailService
import com.project.email.TextBodyResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mails/send")
class SendEmailController(private val emailService: SendEmailService) {

    @PostMapping("/simple")
    fun sendSimpleEmail(@RequestParam("to") to: String, @RequestBody textBodyResponse: TextBodyResponse): String {
        emailService.sendSimpleEmail(to, textBodyResponse.subject, textBodyResponse.text)
        return emailService.addTextMessageToDb(to, textBodyResponse.subject, textBodyResponse.text)
    }

    @PostMapping("/html")
    fun sendHtmlEmail(@RequestParam("to") to: String, @RequestBody htmlBodyResponse: HTMLBodyResponse): String {
        emailService.sendHtmlEmail(to, htmlBodyResponse.subject, htmlBodyResponse.text)
        return emailService.addHtmlMessageToDb(to, htmlBodyResponse.subject, htmlBodyResponse.text)
    }

}