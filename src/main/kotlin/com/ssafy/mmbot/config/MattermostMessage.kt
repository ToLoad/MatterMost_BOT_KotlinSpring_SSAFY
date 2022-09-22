package com.ssafy.mmbot.config

data class MattermostMessage(
    val color: String,
    val author_name: String,
    val author_icon: String,
    val author_link: String,
    val title: String,
    val text: String,
    val image_url: String,
    val footer: String
)