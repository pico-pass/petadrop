package work.jeje.petadrop.config.auth

import jakarta.servlet.http.HttpSession
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import work.jeje.petadrop.config.auth.dto.OAuthAttributes
import work.jeje.petadrop.config.auth.dto.SessionUser
import work.jeje.petadrop.domain.user.User
import work.jeje.petadrop.domain.user.UserRepository

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    private val httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()
        val oAuth2User: OAuth2User = delegate.loadUser(userRequest)

        val registrationId: String = userRequest.clientRegistration.registrationId
        val userNameAttributeName: String = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        val attributes: OAuthAttributes =
            OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.attributes)

        val user: User = saveOrUpdate(attributes)

        httpSession.setAttribute("user", SessionUser(user))

        val authorities: Set<GrantedAuthority> = setOf(SimpleGrantedAuthority(user.getRoleKey()))
        val userAttributes: Map<String, Any> = attributes.attributes
        val nameAttributeKey: String = attributes.nameAttributeKey

        return DefaultOAuth2User(authorities, userAttributes, nameAttributeKey)
    }

    private fun saveOrUpdate(attributes: OAuthAttributes): User {
        val user: User = userRepository.findByEmail(attributes.email)
            .map { entity -> entity.update(attributes.name, attributes.picture) }
            .orElse(attributes.toEntity())

        return userRepository.save(user)
    }
}