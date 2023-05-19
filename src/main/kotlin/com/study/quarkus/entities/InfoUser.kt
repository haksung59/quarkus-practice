package com.study.quarkus.entities

import com.study.quarkus.utils.NoArg
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@NoArg
@Entity
@Table(name = "info_user")
class InfoUser (

    @Id
    @Column(name = "user_id", nullable = false, length = 12)
    var id: String? = null,

    @Column(name = "pw", nullable = false, length = 100)
    var pw: String? = null,

    @Column(name = "username", nullable = false, length = 12)
    var username: String? = null,

    @Column(name = "pwErrCnt")
    var pwErrCnt: Int? = 0,

): BaseEntity()