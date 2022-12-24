package com.project.email.Controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/info")
class GetInfoController {
    @GetMapping()
    fun getInfo(): String {
        return "Get\n\n" +

        "/mails/get/inbox - All emails from INBOX\n" +
        "/mails/get/inbox/{id} - Find email from INBOX by its id\n" +
        "/mails/get/inbox/count - Count of emails from INBOX\n" +
        "/mails/get/spam - All emails from Spam folder\n" +
        "/mails/get/trash - All emails from Trash folder\n" +
        "/mails/get/sent - All sent emails\n" +
        "/mails/get/sent/{id} - Find email from Sent by its id\n" +
        "/mails/get/sent/count - Count of emails from Sent\n\n" +

        "Post\n\n" +

        "/mails/send/simple - Send simple email\n" +
        "/mails/send/html - Send html email\n\n" +

        "Update\n\n" +

        "/mails/update/inbox/seen/{id} - Set email from INBOX 'seen' by its id\n" +
        "/mails/update/inbox/unseen/{id} - Set email from INBOX 'unseen' by its id\n" +
        "/mails/update/inbox/flagged/{id} - Set email from INBOX 'flagged' by its id\n" +
        "/mails/update/inbox/unflagged/{id} - Set email from INBOX 'unflagged' by its id\n" +
        "/mails/update/sent/flagged/{id} - Set email from Sent 'flagged' by its id\n" +
        "/mails/update/sent/unflagged/{id} - Set email from Sent 'unflagged' by its id\n" +
        "/mails/update/inbox/trash/{id} - Move email from INBOX to Trash folder\n" +
        "/mails/update/sent/trash/{id} - Move email from Sent to Trash folder\n\n" +

        "Delete\n\n" +

        "/mails/delete/inbox/{id} - Delete email from INBOX by its id\n" +
        "/mails/delete/spam/{id} - Delete email from Spam folder by its id\n" +
        "/mails/delete/sent/{id} - Delete email from Sent by its id\n" +
        "/mails/delete/trash/{id} - Delete email from Trash folder by its id"
    }
}