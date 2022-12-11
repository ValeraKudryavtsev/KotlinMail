package com.project.email.Controllers

import com.project.email.Services.DeleteEmailService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mails/delete")
class DeleteEmailController(private val deleteEmailService: DeleteEmailService) {
    @DeleteMapping("/inbox/{id}")
    fun deleteFromInbox(@PathVariable id: Int) {
        deleteEmailService.deleteFromInbox(id)
    }

    @DeleteMapping("/spam/{id}")
    fun deleteFromSpam(@PathVariable id: Int) {
        deleteEmailService.deleteFromSpam(id)
    }

    @DeleteMapping("/sent/{id}")
    fun deleteFromSent(@PathVariable id: Long) {
        deleteEmailService.deleteFromSent(id)
    }

    @DeleteMapping("/trash/{id}")
    fun deleteFromTrash(@PathVariable id: Int) {
        deleteEmailService.deleteFromTrash(id)
    }

}