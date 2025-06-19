package com.ecs.sms.features.auth.data.user

import com.ecs.sms.features.auth.domain.models.Role
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class UserEntity(

    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false)
    val hashedPassword: String = "",

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val roles: MutableSet<Role> = mutableSetOf()
)
