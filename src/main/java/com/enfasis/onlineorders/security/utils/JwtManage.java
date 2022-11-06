package com.enfasis.onlineorders.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enfasis.onlineorders.constants.Strings;
import com.enfasis.onlineorders.exeption.custom.BadDataException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtManage {

    private String jwtSecret;

    public JwtManage(@Value("${jwt-secret-key}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String generateToken(Long id, String username) throws BadDataException {
        Map<String, Object> claims = new HashMap<>();
        if (id == null || username == null || "".equals(username)
                || jwtSecret == null || "".equals(jwtSecret)) {
            throw new BadDataException(Strings.BAD_DATA_FOR_TOKEN_GENERATION);
        }
        claims.put(Strings.USER_ID_BODY, id);
        return doGenerateToken(claims, username);
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        return JWT.create().
                withSubject(username).withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
                .withPayload(claims)
                .sign(algorithm);
    }

    public Long verifyToken(String authorizationHeader) {
        if (authorizationHeader == null || "".equals(authorizationHeader)
                || jwtSecret == null || "".equals(jwtSecret)) {
            throw new BadDataException(Strings.BAD_DATA_FOR_TOKEN_VERIFICATION);
        }
        String token = authorizationHeader.substring(Strings.BEARER.length());
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Claim id = decodedJWT.getClaim(Strings.USER_ID_BODY);
        return id.asLong();
    }
}
