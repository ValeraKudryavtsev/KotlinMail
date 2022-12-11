package com.project.email.Repos

import com.project.email.Domain.SentMessages
import org.springframework.data.repository.CrudRepository

interface SentMessagesRepo : CrudRepository<SentMessages, Long>{

}