package work.jeje.petadrop.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.security.Principal


@RestController
class IndexController {
    @GetMapping("/")
    fun index2() : ResponseEntity<Any>
    {
        val headers: HttpHeaders = HttpHeaders()


        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        return if (authentication != null && authentication.isAuthenticated) {
            headers.location = URI.create("/user")
            ResponseEntity<Any>(headers, HttpStatus.MOVED_PERMANENTLY)
        } else {
            headers.location = URI.create("/login")
            ResponseEntity<Any>(headers, HttpStatus.MOVED_PERMANENTLY)
        }
    }
    @GetMapping("/user")
    fun index(principal: Principal) : Principal
    {
        return principal
    }
}