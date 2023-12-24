package work.jeje.petadrop.config.auth.dto

import work.jeje.petadrop.domain.user.User
import java.io.Serializable

data class SessionUser(
    val name: String,
    val email: String,
    val picture: String
) : Serializable {
    constructor(user: User) : this(
        name = user.name,
        email = user.email,
        picture = user.picture!!
    )
}