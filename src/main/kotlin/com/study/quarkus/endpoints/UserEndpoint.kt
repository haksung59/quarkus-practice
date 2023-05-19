package com.study.quarkus.endpoints

import com.study.quarkus.dtos.LoginRequest
import com.study.quarkus.entities.InfoUser
import com.study.quarkus.services.UserService
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.annotation.security.PermitAll
import javax.inject.Inject
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Tag(name = "user endpoint")
@Path("api/v1/public")
@PermitAll
class UserEndpoint {

    @Inject
    private lateinit var userService: UserService

    @POST
    @Path("/join")
    @PermitAll
    fun join(infoUser: InfoUser){
        userService.join(infoUser)
    }

    @POST
    @Path("/login")
    fun login(loginRequest: LoginRequest): Response {
        return Response.ok(userService.login(loginRequest)).build()
    }

}