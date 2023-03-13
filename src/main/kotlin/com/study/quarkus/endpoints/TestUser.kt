package com.study.quarkus.endpoints

import com.study.quarkus.entities.InfoUser
import com.study.quarkus.services.UserService
import io.quarkus.cache.CacheInvalidate
import io.quarkus.cache.CacheKey
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.jboss.logging.Logger
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path

@Tag(name = "test", description = "test API")
@Path("api/v1")
class TestUser {

    @Inject
    lateinit var service: UserService

    @POST
    @Path("/join")
    @PermitAll
    fun join(@RequestBody infoUser: InfoUser){
        service.join(infoUser)
    }

    @POST
    @Path("/login")
    @PermitAll
    fun login(@RequestBody infoUser: InfoUser){
        service.login(infoUser)
    }

    @GET
    @Path("/home")
    @CacheInvalidate(cacheName = "jwt")
    fun home(@CacheKey cacheKey: String){
        service.home(cacheKey)
    }

}