package com.ssafy.mmbot.send

import com.google.gson.Gson
import com.ssafy.mmbot.config.MattermostMessage
import lombok.RequiredArgsConstructor
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
@RequiredArgsConstructor
class MattermostSender {
    private val log = LoggerFactory.getLogger(javaClass) // 코틀린에서는 아직 @Slf4j 를 지원하지 않아 이와 같이 생성해서 사용

    @Value("\${notification.mattermost.webhook-url}")
    private lateinit var webhookUrl: String
    @Value("\${notification.mattermost.author-name}")
    private lateinit var authorName: String
    @Value("\${notification.mattermost.author-icon}")
    private lateinit var authorIcon: String
    @Value("\${notification.mattermost.author-link}")
    private lateinit var authorLink: String

    private var footer: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))


    fun sendMessage(title: String, text: String, color: String, imageUrl: String) {
        try {
            val message = MattermostMessage(color, authorName, authorIcon, authorLink, title, text, imageUrl, footer)
            val map: HashMap<String, ArrayList<MattermostMessage>> = HashMap()

            val list: ArrayList<MattermostMessage> = ArrayList()
            list.add(message)
            map["attachments"] = list

            val payload = Gson().toJson(map)

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