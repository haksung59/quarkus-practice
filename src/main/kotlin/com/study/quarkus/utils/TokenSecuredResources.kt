package com.study.quarkus.utils

import org.eclipse.microprofile.jwt.JsonWebToken
import javax.annotation.security.PermitAll
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext


@Path("/secured")
class TokenSecuredResource() {

    @Inject
    lateinit var jwt : JsonWebToken

    @GET
    @Path("permit-all")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    fun hello(@Context ctx: SecurityContext): String {
        return getResponseString(ctx)
    }

    private fun getResponseString(ctx: SecurityContext): String {
        val name: String
        if (ctx.userPrincipal == null) {
            name = "anonymous"
        } else if (!ctx.userPrincipal.name.equals(jwt.name)) {
            throw InternalServerErrorException("Principal and JsonWebToken names do not match")
        } else {
            name = ctx.userPrincipal.name
        }
        return java.lang.String.format(
            "hello + %s,"
                    + " isHttps: %s,"
                    + " authScheme: %s,"
                    + " hasJWT: %s",
            name, ctx.isSecure, ctx.authenticationScheme, hasJwt()
        )
    }

    private fun hasJwt(): Boolean {
        return jwt.claimNames != null
    }
}