package com.study.quarkus.entities

import kotlinx.serialization.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Serializable
@Entity
@Table(name = "info_user")
class InfoUser (

    @Id
    @Column(name = "user_id", nullable = false, length = 10)
    var id: String? = null,

    @Column(name = "pw", nullable = false, length = 100)
    var pw: String? = null

): BaseEntity(), java.io.Serializable {
    companion object {
        private const val serialVersionUID: Long = 1111111111111L
    }
}