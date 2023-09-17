# MatterMost BOT /w KotlinSpring

This is Scheduler built with KotlinSpring.

I made it because I wanted someone to tell me the schedule when I was taking SSAFY(Programming Education).

![mm](https://img.shields.io/badge/Mattermost-0058CC?style=for-the-badge&logo=Mattermost&logoColor=white)
![docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)

![spring](https://img.shields.io/badge/Spring%20Boot-2.7.3-green?style=flat&logo=Spring-Boot)
![java](https://img.shields.io/badge/openjdk-17.0.4.1-orange?style=flat&logo=OpenJdk)
![kotlin](https://img.shields.io/badge/Kotlin-1.6-blue?style=flat&logo=Kotlin)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


---
## Guide in KR
### 📜 MatterMost Webhook URL 발급

통합으로 진입 후 **Incoming webhook 추가하기** 클릭
![mm_web_hook1](/asset/mm_web_hook1.PNG)

제목 및 설명, 원하는 채널 선택 후 저장
![mm_web_hook2](/asset/mm_web_hook2.PNG)

생성된 Webhook URL 복사
![mm_web_hook3](/asset/mm_web_hook3.png)

application.yml에 붙여넣기
```yaml
notification:
  mattermost:
    webhook-url: "Your Webhook URL Here"
```

---

### ✒ MatterMost message Custom

MessageSender 파일에서 원하는 값으로 수정하면 된다.
```kotlin
// config/MessageSender.kt

// init your custom contents 원하는 값으로 변경
private val preText: String = "@here"
private val authorName: String =  "카드 메시지 상단 이름"
private val authorIcon: String =  "추가할 이미지 URL"
private val authorLink: String =  "상단 이름에 추가할 링크"
private val footer: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
// end custom contents

// 원하는 메시지와 사진을 Parameter로 넘기면 전송됨.
fun sendMessage(isHere: Boolean, title: String, text: String, color: String, imageUrl: String)
```

실제 전송 모습과 Parameter
![param](/asset/param.png)

---

### ⌛ Test
TestController 에서 작성된 커스텀 메시지의 작동 여부확인
```kotlin
@GetMapping("/in")
fun goIn(): String {
    messageSender.sendMessage(true, "Your Title", "Your Text", "Sidebar Color", "imageUrl")
    return "in Test"
}
```
---

### ⏰ Message Scheduling
ScheduledTasks에서 작성된 예시 참조
```kotlin
// 평일 아침 8시 55분 마다 입실 여부를 체크하는 메시지 전송
@Scheduled(cron = "0 55 8 ? * MON-FRI")
fun entrance() {
    val imageUrl = "Your ImageURL Here"
    messageSender.sendMessage(true, "입실 체크 하셨나요 ?", "오늘도 화이팅 !!", "#b2e342", imageUrl)
}
```

---


### ⛏ 프로젝트 빌드하기

```shell
./gradlew build
```

---

### 🖥 프로젝트 서버에 배포하기
1. 로컬에서 [빌드](#-프로젝트-빌드하기) 후 /build/libs/*.jar 파일 생성 여부 확인
2. 확인 후 로컬에서 도커라이징 진행
```shell
docker build -t mmbot:0.1 .
```

3. 배포할 서버에서 실행
```shell
docker run -d --name mmbot -p 8080:8080 -e TZ=Asia/Seoul mmbot:0.1
```
