package com.ssafy.mmbot.config

import com.google.gson.Gson
import com.ssafy.mmbot.vo.MsgNoPreTextVO
import com.ssafy.mmbot.vo.MsgWithPreTextVO


class MessageInfo (
    private val pretext: String,
    private val color: String,
    private val author_name: String,
    private val author_icon: String,
    private val author_link: String,
    private val title: String,
    private val text: String,
    private val image_url: String,
    private val footer: String
) {
    fun makeJson(isHere: Boolean): String {
        if (isHere) {
            val message = MsgWithPreTextVO(pretext, color, author_name, author_icon, author_link, title, text, image_url, footer)
            val map: HashMap<String, ArrayList<MsgWithPreTextVO>> = HashMap()

            val list: ArrayList<MsgWithPreTextVO> = ArrayList()
            list.add(message)
            map["attachments"] = list
            return Gson().toJson(map)
        } else {
            val message = MsgNoPreTextVO(color, author_name, author_icon, author_link, title, text, image_url, footer)
            val map: HashMap<String, ArrayList<MsgNoPreTextVO>> = HashMap()

            val list: ArrayList<MsgNoPreTextVO> = ArrayList()
            list.add(message)
            map["attachments"] = list
            return Gson().toJson(map)
        }
    }
}