package com.study.quarkus.utils

import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.jwt.Claims
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.logging.Logger
import javax.inject.Inject
import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

class TokenSecuredResource {

    companion object {

        @Produces(MediaType.TEXT_PLAIN)
        fun helloRolesAllowed(@Context ctx: SecurityContext, jwt: JsonWebToken): String {
            return getResponseString(ctx, jwt) + ", birthdate: " + jwt.getClaim<Any?>("birthdate").toString()
        }

        private fun getResponseString(ctx: SecurityContext, jwt: JsonWebToken): String {
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
                name, ctx.isSecure, ctx.authenticationScheme, hasJwt(jwt)
            )
        }

        private fun hasJwt(jwt: JsonWebToken): Boolean {
            return jwt.claimNames != null
        }


        fun generateToken(userId: String): String {
            return Jwt.issuer("https://example.com/issuer")
                .upn(userId)
                .groups("User")
                .claim(
                    Claims.birthdate.name, "1993-05-22"
                ).sign()
        }
    }

}