package com.project.email.Controllers

import com.project.email.Services.GetEmailService
import com.project.email.Services.UpdateEmailService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mails/update")
class UpdateEmailController(private val updateEmailService: UpdateEmailService) {
    @PutMapping("/inbox/seen/{id}")
    fun setInboxEmailSeen(@PathVariable id: Int) {
        updateEmailService.setInboxEmailSeen(id)
    }

    @PutMapping("/inbox/unseen/{id}")
    fun setInboxEmailUnseen(@PathVariable id: Int) {
        updateEmailService.setInboxEmailUnseen(id)
    }

    @PutMapping("/inbox/flagged/{id}")
    fun setInboxEmailFlagged(@PathVariable id: Int) {
        updateEmailService.setInboxEmailFlagged(id)
    }

    @PutMapping("/inbox/unflagged/{id}")
    fun setInboxEmailUnflagged(@PathVariable id: Int) {
        updateEmailService.setInboxEmailUnflagged(id)
    }

    @PutMapping("/sent/flagged/{id}")
    fun setSentEmailFlagged(@PathVariable id: Long) {
        updateEmailService.setSentEmailFlagged(id)
    }

    @PutMapping("/sent/unflagged/{id}")
    fun setSentEmailUnflagged(@PathVariable id: Long) {
        updateEmailService.setSentEmailUnflagged(id)
    }

    @PutMapping("/inbox/trash/{id}")
    fun moveInboxEmailToTrash(@PathVariable id: Int) {
        updateEmailService.moveInboxEmailToTrash(id)
    }

    @PutMapping("/sent/trash/{id}")
    fun moveSentEmailToTrash(@PathVariable id: Long) {
        updateEmailService.moveSentEmailToTrash(id)
    }

}