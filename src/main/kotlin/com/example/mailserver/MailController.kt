package com.example.mailserver

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mail")
class MailController(
    private val msender: MailImpl
) {
    
    val logger = LoggerFactory.getLogger(MailController::class.java)
    
    @GetMapping("/simple")
    fun simplemail(@RequestBody mailinfo: MailPack) {
        return msender.sendSimple(mailinfo.recipients, mailinfo.subject, mailinfo.body)
    }
    
    @GetMapping("/mime")
    fun mimemail(@RequestBody mailinfo: MailPack) {
        return msender.sendMime(mailinfo.recipients, mailinfo.subject, mailinfo.body)
    }
    
    @GetMapping("/checkalive")
    fun livecheck(): String {
        logger.debug("sender is retrieved as ${msender.sender}")
        return "you reached email api. We are a go."
    }
}