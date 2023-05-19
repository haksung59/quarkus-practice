package com.study.quarkus.utils

import com.study.quarkus.entities.BaseEntity
import org.eclipse.microprofile.jwt.JsonWebToken
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@ApplicationScoped
class BaseEntityListener {

    @Inject
    private lateinit var jsonWebToken: JsonWebToken

    @PrePersist
    fun prePersist(entity: BaseEntity) {
        if (jsonWebToken.name != null) {
            entity.rgstUserId = jsonWebToken.name
            entity.updtUserId = jsonWebToken.name
        }
    }

    @PreUpdate
    fun preUpdate(entity: BaseEntity) {
        if (jsonWebToken.name != null) {
            entity.updtUserId = jsonWebToken.name
        }
    }
}