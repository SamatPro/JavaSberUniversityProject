package ru.itis.utils;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ignored) {
            return false;
        }
    }

}
