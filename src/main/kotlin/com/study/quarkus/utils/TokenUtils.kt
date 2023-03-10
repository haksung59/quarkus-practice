package com.study.quarkus.utils

import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.jwt.Claims
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.logging.Logger
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext


@Path("/secured")
@RequestScoped
class TokenSecuredResource {

    @Inject
    lateinit var jwt : JsonWebToken

    @Inject
    lateinit var log: Logger

    @GET
    @Path("permit-all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(@Context ctx: SecurityContext): String {
        return getResponseString(ctx)
    }

    @GET
    @Path("roles-allowed")
    @RolesAllowed("User", "Admin")
    @Produces(MediaType.TEXT_PLAIN)
    fun helloRolesAllowed(@Context ctx: SecurityContext) : String {
        return getResponseString(ctx) + ", birthdate: " + jwt.getClaim<Any?>("birthdate").toString()
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

    @GET
    @Path("generate-token")
    @PermitAll
    fun generateToken(): String {
        return Jwt.issuer("https://example.com/issuer")
            .upn("id")
            .groups("User")
            .claim(
                Claims.birthdate.name, "1993-05-22"
            ).sign()
    }

}