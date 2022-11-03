package com.ssafy.mmbot.controller

import com.ssafy.mmbot.config.MessageSender
import com.ssafy.mmbot.vo.ImageUrlVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @Autowired
    private lateinit var imageUrlVO: ImageUrlVO
    private lateinit var messageSender: MessageSender

    @GetMapping("/")
    fun home(): String {
        return "/in 으로 이동시 입실체크, /out 으로 이동시 퇴실체크 알림이 전송됩니다."
    }

    @GetMapping("/in")
    fun goIn(): String {
        messageSender.sendMessage(true, "입실 체크 하셨나요 ?", "오늘도 화이팅 !!", "#b2e342", imageUrlVO.inImageUrl)
        return "in Test"
    }

    @GetMapping("/out")
    fun goOut(): String {
        messageSender.sendMessage(false, "퇴실 체크 하셨나요 ?", "오늘도 수고하셨습니다 !!", "#ff5d52", imageUrlVO.outImageUrl)
        return "out Test"
    }

    @GetMapping("/firefighter")
    fun goFirefighter(): String {
        val imageUrl = "https://j.gifs.com/16PAVP.gif"
        messageSender.sendMessage(false, "시켜줘..", "부울경 명예소방관", "#ff5d52", imageUrl)
        return "Go Firefighter"
    }

    @PostMapping("/in/modify")
    fun modifyInUrl(@RequestBody param: Map<String, String>): ResponseEntity<String> {
        val url = param.getOrDefault("url", "null")
        if (url == "null") {
            return ResponseEntity.status(404).body("URL이 입력되지 않았습니다.")
        }

        imageUrlVO.inImageUrl = url
        return ResponseEntity.status(200).body("다음 URL로 변경 : ${imageUrlVO.inImageUrl}")
    }

    @PostMapping("/out/modify")
    fun modifyOutUrl(@RequestBody param: Map<String, String>): ResponseEntity<String> {
        val url = param.getOrDefault("url", "null")
        if (url == "null") {
            return ResponseEntity.status(404).body("URL이 입력되지 않았습니다.")
        }

        imageUrlVO.outImageUrl = url
        return ResponseEntity.status(200).body("다음 URL로 변경 : ${imageUrlVO.outImageUrl}")
    }
}