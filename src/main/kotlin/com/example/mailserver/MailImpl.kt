package com.example.mailserver

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import javax.mail.Address
import javax.mail.Message
import javax.mail.internet.InternetAddress


@Component
class MailImpl(
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.username}") val sender: String
) {
    val logger = LoggerFactory.getLogger(MailImpl::class.java)
    
    fun sendMime(recipient: List<String>, subj: String, body: String) {
        val message = javaMailSender.createMimeMessage()
        val newRecipList = recipient.joinToString(",")
        logger.debug("concatenated recipient list: $newRecipList")
        
        message.setFrom(sender) //sender
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(newRecipList))
        
        message.subject = subj
        message.setText(body)
        return javaMailSender.send(message) //send
    }
    
    
    fun sendSimple(recipient: List<String>, subj: String, body: String) {
        val msg = SimpleMailMessage()
        msg.setFrom(sender)
        logger.debug("sender : $sender")
//        val recip1 = "${recipient[0]}"
        val newRecipList = recipient.toTypedArray()
        msg.setTo(*newRecipList)
        logger.debug("recipient: $newRecipList")
        msg.setSubject(subj)
        logger.debug("subject: $subj")
        msg.setText(body)
        logger.debug("body: $body")
        return javaMailSender.send(msg)
    }
}