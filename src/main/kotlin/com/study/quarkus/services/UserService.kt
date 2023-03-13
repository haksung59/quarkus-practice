package com.study.quarkus.services

import com.study.quarkus.entities.InfoUser
import com.study.quarkus.repositories.UserRepository
import com.study.quarkus.utils.TokenSecuredResource
import io.quarkus.cache.CacheManager
import io.quarkus.cache.CacheResult
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.Cacheable
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
    @CacheResult(cacheName = "jwt")
    fun login(infoUser: InfoUser) :String {
        var findUser = repository.findByUserId(infoUser.id!!)
        lateinit var strToken: String

        if(infoUser.pw.equals(findUser!!.pw)){
            strToken = "Bearer " + TokenSecuredResource.generateToken(findUser.id!!)
        }

        return strToken
    }

    @Transactional
    fun home(cacheKey: String){
        log.info(cacheKey)
    }

}