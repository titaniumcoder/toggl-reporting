package io.github.titaniumcoder.reporting.config

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import io.github.titaniumcoder.reporting.exceptions.InvalidTokenException
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GoogleValidationService(val config: ReportingConfiguration) : TokenValidationService {
    private val verifier: GoogleIdTokenVerifier by lazy {
        val transport = NetHttpTransport()
        val jsonFactory = JacksonFactory()

        GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(listOf(config.clientId))
                .build()
    }

    @Cacheable("tokens")
    override fun validateIdToken(idToken: String): Mono<String> {
        return Mono.fromCallable {
            val result = verifier.verify(idToken)

            if (result != null) {
                val payload = result.payload

                val email = payload.email
                val verified = payload.emailVerified

                if (verified) {
                    log.debug("Loading user with email $email")
                    email
                } else {
                    throw InvalidTokenException(idToken)
                }
            } else {
                throw InvalidTokenException(idToken)
            }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(GoogleValidationService::class.java)
    }
}

