package se.jensen.meiying.socialapp.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * {@link JwtUtil} är en hjälparklass för hantering av JSON Web Tokens (JWT).
 * <p>
 * Den används för att generera, validera och extrahera information från JWT, såsom användarnamn.
 * Den stöder både vanliga access-tokens och refresh-tokens.
 * </p>
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    @Value("${jwt.refresh-expiration-ms}")
    private long refreshExpirationMs;

    /**
     * Genererar en ny JWT access-token för en given användare.
     *
     * @param username användarnamnet som token ska representera.
     * @return en signerad JWT access-token som sträng.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /**
     * Genererar en ny JWT refresh-token för en given användare.
     *
     * @param username användarnamnet som token ska representera.
     * @return en signerad JWT refresh-token som sträng.
     */
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /**
     * Validerar en JWT-token.
     * <p>
     * Metoden kontrollerar att token är korrekt signerad och inte har gått ut.
     * </p>
     *
     * @param token JWT-token som ska valideras.
     * @return {@code true} om token är giltig, annars {@code false}.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extraherar användarnamnet från en JWT-token.
     *
     * @param token JWT-token som innehåller användarnamnet.
     * @return användarnamnet (subject) från token.
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
