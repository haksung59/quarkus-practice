package com.study.quarkus.repositories

import com.study.quarkus.entities.InfoUser
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository : PanacheRepository<InfoUser>{

    fun findByUserId(userId: String) = find("id", userId).firstResult()

}
