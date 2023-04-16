import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.jwt.JsonWebToken
import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

class TokenUtils {

    companion object {

        @Produces(MediaType.TEXT_PLAIN)
        fun helloRolesAllowed(@Context ctx: SecurityContext, jwt: JsonWebToken): String {
            return debugJwtContent(jwt, ctx)// + ", birthdate: " + jwt.claim<Any?>("birthdate").toString()
        }
        private fun hasJwt(jwt: JsonWebToken): Boolean {
            return jwt.claimNames != null
        }

        fun debugJwtContent(jwt: JsonWebToken, ctx: SecurityContext): String {
            val name = if (ctx.userPrincipal == null) {
                "anonymous"
            } else if (!ctx.userPrincipal.name.equals(jwt.name)) {
                throw InternalServerErrorException("Principal and JsonWebToken names do not match")
            } else {
                ctx.userPrincipal.name
            }

            return "hello $name isHttps: ${ctx.isSecure} authScheme: ${ctx.authenticationScheme} hasJWT: ${hasJwt(jwt)}" +
                    " issueAt: ${jwt.issuedAtTime}" + " expiresAt: ${jwt.expirationTime} userType: ${jwt.groups}"
        }

        fun generateUserToken(userId: String): String {
            return Jwt.issuer("https://example.com/issuer")
                    .upn(userId)
                    .expiresIn(60 * 60 * 24 * 30)//30일
                    .groups("USER")
                    .sign()
        }

    }
}