package com.ssafy.mmbot.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class MessageSender {
    private val log = LoggerFactory.getLogger(javaClass)

    @Value("\${notification.mattermost.webhook-url}")
    private val webhookUrl: String = ""

    // init your custom contents
    private val preText: String = "@here"
    private val authorName: String =  "===== Edu SSAFY ====="
    private val authorIcon: String =  "https://edu.ssafy.com/"
    private val authorLink: String =  "data:image/png;base64,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"
    private val footer: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    // end custom contents

    /**
     * Send Mattermost Card message in your channel
     * [MattermostDocs](https://developers.mattermost.com/integrate/reference/message-attachments/)
     *
     * @param isHere true: Add preText(Default : "@here") before your card message
     * @param title Title in your card message
     * @param text Text contents in your card message
     * @param color Line color displayed on the left side of the card message
     * @param imageUrl ImageURL to be displayed inside the card
     *
     * @author An 'ToLoad' Youngwon
     * @since 2022.09.23
     */
    fun sendMessage(isHere: Boolean, title: String, text: String, color: String, imageUrl: String) {
        try {
            val messageInfo = MessageInfo(preText, color, authorName, authorIcon, authorLink, title, text, imageUrl, footer)
            val payload = messageInfo.makeJson(isHere)

            val headers = HttpHeaders()
            headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE)

            val entity: HttpEntity<String> = HttpEntity(payload, headers)
            RestTemplate().postForEntity(webhookUrl, entity, String::class.java)
        } catch (e: Exception) {
            log.error(e.message)
            log.error("==================== ERROR : MattermostSender.sendMessage ====================")
        }
    }
}