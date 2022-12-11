package com.project.email

import java.util.*

data class MessageInfo (
    var id: Int,
    var from: String,
    var to: String,
    var subject: String,
    var folder: String,
    var date: Date,
    var contentType: String,
    var flags: String
)

data class MessageInfoById (
    var id: Int,
    var from: String,
    var to: String,
    var subject: String,
    var folder: String,
    var date: Date,
    var contentType: String,
    var flags: String,
    var content: String
)

data class MessageCount (
    var count: Int
)

data class TextBodyResponse (
    var subject: String,
    var text: String
)

data class HTMLBodyResponse (
    var subject: String,
    var text: String
)