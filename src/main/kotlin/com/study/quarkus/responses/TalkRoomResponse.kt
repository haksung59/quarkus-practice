package com.study.quarkus.responses

import java.time.Instant

data class TalkRoomResponse(
    var tkrmId: String? = null,
    var tkrmNm: String? = null,
    var msg: String? = null,
    var rgstDttm: Instant? = null
)