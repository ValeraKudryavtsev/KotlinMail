package com.project.email.Domain

import java.util.*
import javax.persistence.*

@Entity
class SentMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    open val id: Long = 0

    @Column (name = "from_m")
    lateinit var from: String
    @Column (name = "to_m")
    lateinit var to: String
    lateinit var subject: String
    lateinit var folder: String
    lateinit var date: String
    lateinit var contentType: String
    lateinit var flags: String
    lateinit var content: String
}