package ru.itis.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.models.Customer;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    public String generateToken(Customer customer) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setSubject(Long.toString(customer.getId()))
                .claim("email", customer.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }

    public Long getCustomerIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public String getEmailFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    public boolean validateToken(String authToken) {
        try {
            authToken = authToken.substring("Bearer".length()).trim();
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ignored) {
            throw new SecurityException("Token failed validation");
        }
    }
}
