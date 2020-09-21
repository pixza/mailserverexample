package com.example.mailserver

data class MailPack(
    val recipients: List<String>,
    val subject: String,
    val body: String
)