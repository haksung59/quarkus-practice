package com.study.quarkus.endpoints

import com.study.quarkus.services.UserService
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.jboss.logging.Logger
import javax.inject.Inject
import javax.ws.rs.Path

@Tag(name = "test", description = "test API")
@Path("api/v1")
class TestUser {

    @Inject
    lateinit var log: Logger

    @Inject
    lateinit var service: UserService

    @Operation

}