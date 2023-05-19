package com.study.quarkus.entities

import com.study.quarkus.utils.BaseEntityListener
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(BaseEntityListener::class)
abstract class BaseEntity (

    @Column(name = "rgst_user_id", nullable = false, length = 12)
    var rgstUserId: String? = null,

    @Column(name = "rgst_dttm", nullable = false, updatable = false)
    @CreationTimestamp
    var rgstDttm: Instant? = null,

    @Column(name = "updt_user_id", nullable = false, length = 12)
    var updtUserId: String? = null,

    @Column(name = "updt_dttm", nullable = false)
    @UpdateTimestamp
    var updtDttm: Instant? = null,

    )