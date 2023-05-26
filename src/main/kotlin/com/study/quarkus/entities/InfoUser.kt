package com.study.quarkus.entities

import com.study.quarkus.utils.NoArg
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@NoArg
@Entity
@Table(name = "info_user")
class InfoUser (

    @Id
    @Column(name = "user_id", nullable = false, length = 15)
    var id: String? = null,

    @Column(name = "pw", nullable = false, length = 100)
    var pw: String? = null,

    @Column(name = "username", nullable = false, length = 12)
    var username: String? = null,

    @Column(name = "birth", nullable = false, length = 6)
    var birth: String? = null,

    @Column(name = "pwErrCnt")
    var pwErrCnt: Int? = 0,

    @Column(name = "rgst_user_id", nullable = false, length = 15)
    var rgstUserId: String? = null,

    @Column(name = "rgst_dttm", nullable = false, updatable = false)
    @CreationTimestamp
    var rgstDttm: Instant? = null,

    @Column(name = "updt_user_id", nullable = false, length = 15)
    var updtUserId: String? = null,

    @Column(name = "updt_dttm", nullable = false)
    @UpdateTimestamp
    var updtDttm: Instant? = null,

    )