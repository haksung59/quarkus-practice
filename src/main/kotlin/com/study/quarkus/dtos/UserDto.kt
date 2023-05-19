package com.study.quarkus.dtos

import com.study.quarkus.utils.NoArg

@NoArg
data class LoginRequest(
    var id: String,
    var pw: String
)