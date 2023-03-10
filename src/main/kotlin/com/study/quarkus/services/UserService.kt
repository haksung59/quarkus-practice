package com.study.quarkus.services

import com.study.quarkus.entities.InfoUser
import com.study.quarkus.repositories.UserRepository
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class UserService {

    @Inject
    lateinit var log: Logger

    @Inject
    lateinit var jwt: JsonWebToken

    @Inject
    lateinit var repository : UserRepository

    @Transactional
    fun join(request: InfoUser) {
        request.rgstUserId=request.id
        request.updtUserId=request.id
        repository.persist(request)
    }

    @Transactional
    fun login(infoUser: InfoUser){
        var findUser = repository.findByUserId(infoUser.id!!)

        if(infoUser.pw.equals(findUser!!.pw)){
            log.debug("비밀번호 일치")
        }
    }

}