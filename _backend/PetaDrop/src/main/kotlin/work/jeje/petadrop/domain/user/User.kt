package work.jeje.petadrop.domain.user

import jakarta.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var email: String,

    @Column
    var picture: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role,

    ) {
    constructor() : this(null, "", "", null, Role.USER)

    fun update(name: String, picture: String): User {
        this.name = name
        this.picture = picture
        return this
    }

    fun getRoleKey(): String {
        return this.role.key
    }
}