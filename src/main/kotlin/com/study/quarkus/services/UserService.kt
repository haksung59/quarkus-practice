package com.study.quarkus.services

import com.study.quarkus.dtos.LoginRequest
import com.study.quarkus.entities.InfoUser
import com.study.quarkus.repositories.UserRepository
import com.study.quarkus.responses.CommonResponse
import com.study.quarkus.utils.CustomizedException
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.logging.Logger
import java.time.Instant
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.core.Response

@ApplicationScoped
class UserService {

    @Inject
    lateinit var log: Logger

    @Inject
    lateinit var jwt: JsonWebToken

    @Inject
    private lateinit var userRepository : UserRepository

    @Transactional
    fun join(request: InfoUser) : CommonResponse<String> {
        val existingUser = userRepository.findByUserId(request.id!!)
        return if (existingUser == null) {
            request.rgstUserId=request.id
            request.updtUserId=request.id
            userRepository.persist(request)
            CommonResponse("success")
        } else {
            CommonResponse("중복된 아이디가 있습니다.")
        }
    }

    @Transactional
    fun login(loginRequest: LoginRequest): CommonResponse<String> {
        var findUser = userRepository.findByUserId(loginRequest.id)
            ?: throw CustomizedException("등록된 ID가 없습니다.", Response.Status.NOT_FOUND)

        return when {
            findUser.pwErrCnt!! >= 5 -> {
                throw CustomizedException("비밀번호 불일치 5회 발생", Response.Status.FORBIDDEN)
            }
            findUser.pw.equals(loginRequest.pw) -> {
                findUser.pwErrCnt = 0
                CommonResponse(TokenUtils.generateUserToken(findUser.id!!))
            }
            else -> {
                findUser.pwErrCnt = findUser.pwErrCnt!! + 1
                throw CustomizedException("사용자 정보가 일치하지 않습니다.", Response.Status.BAD_REQUEST)
            }
        }
    }

}