package com.ssafy.mmbot.schedule

import com.ssafy.mmbot.config.MessageSender
import com.ssafy.mmbot.vo.ImageUrlVO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class ScheduledTasks {
    @Autowired
    private lateinit var imageUrlVO: ImageUrlVO
    @Autowired
    private lateinit var messageSender: MessageSender

    private val log = LoggerFactory.getLogger(javaClass)
    private val dataFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

    @Scheduled(cron = "0 55 8 ? * MON-FRI")
    fun entrance() {
        val imageUrl = imageUrlVO.inImageUrl
        messageSender.sendMessage(true, "입실 체크 하셨나요 ?", "오늘도 화이팅 !!", "#b2e342", imageUrl)
    }

    @Scheduled(cron = "0 00 18 ? * MON-FRI")
    fun exit() {
        val imageUrl = imageUrlVO.outImageUrl
        messageSender.sendMessage(true, "퇴실 체크 하셨나요 ?", "오늘도 수고하셨습니다 !!", "#ff5d52", imageUrl)
    }

}