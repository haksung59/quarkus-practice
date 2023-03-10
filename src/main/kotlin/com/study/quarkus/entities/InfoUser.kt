package com.study.quarkus.entities

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "info_user")
open class InfoUser : BaseEntity() {

    @Id
    @Column(name = "user_id", nullable = false, length = 10)
    open var id: String? = null

    @Column(name = "pw", nullable = false, length = 100)
    open var pw: String? = null

}