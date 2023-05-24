package com.study.quarkus.endpoints

import com.study.quarkus.dtos.LoginRequest
import com.study.quarkus.entities.InfoUser
import com.study.quarkus.services.UserService
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.jboss.logging.Logger
import javax.annotation.security.PermitAll
import javax.inject.Inject
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Tag(name = "user endpoint")
@Path("v1/public/user")
@PermitAll
class UserEndpoint {

    @Inject
    private lateinit var userService: UserService

    @Inject
    private lateinit var log: Logger

    @POST
    @Path("join")
    @PermitAll
    fun join(@Valid infoUser: InfoUser): Response{
        log.warn("hi")
        userService.join(infoUser)
        return Response.ok(userService.join(infoUser)).build()
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    fun login(loginRequest: LoginRequest): Response {
        return Response.ok(userService.login(loginRequest)).build()
    }

}