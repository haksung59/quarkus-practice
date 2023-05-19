package com.study.quarkus.responses

import com.study.quarkus.constants.ResultCode
import org.eclipse.microprofile.openapi.annotations.media.Schema
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CommonResponse<T>(
    @NotNull
    @NotEmpty
    @Schema(description = "응답코드", maxLength = 2)
    val code: String,
    @Schema(description = "에러메시지", example = "사용권한이 없습니다")
    val message: String?,
    val result: T?
){
    constructor(result: T): this(ResultCode.SUCCESS, "SUCCESS", result)

    constructor(message: String, result: T): this(ResultCode.SUCCESS, message, result)

}