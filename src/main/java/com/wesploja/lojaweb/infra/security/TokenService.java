package com.wesploja.lojaweb.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wesploja.lojaweb.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Service
public class TokenService {

    @Value("${api.secutiry.token.secret}")
    private String secret;

    @Value("${api.secutiry.token.expiration}")
    private Long expiration;

    @Value("${api.secutiry.token.header}")
    private String headerString;

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(headerString)
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(getExpiration())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error to create token" + exception.getMessage());
        }
    }

    private Instant getExpiration() {
        return Instant.now().plusMillis(expiration);
    }


    public String getSubject(String tokenJwt) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(headerString)
                    .build()
                    .verify(tokenJwt)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Error to verify token" + exception.getMessage());
        }
    }
}
