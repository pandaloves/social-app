package se.jensen.meiying.socialapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * {@link JwtAuthenticationFilter} är ett Spring Security-filter som körs en gång per HTTP-förfrågan.
 * <p>
 * Filtrets uppgift är att extrahera JWT-token från Authorization-headern, validera den
 * och sätta användarens autentisering i {@link SecurityContextHolder} om token är giltig.
 * </p>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    /**
     * Skapar en ny instans av {@link JwtAuthenticationFilter}.
     *
     * @param jwtUtil hjälpbibliotek för hantering av JWT-token, t.ex. validering och extrahering av användarnamn.
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Filtrerar inkommande HTTP-förfrågningar och autentiserar användaren om en giltig JWT-token finns.
     * <p>
     * Steg som utförs:
     * <ol>
     *     <li>Hämta Authorization-headern från HTTP-förfrågan.</li>
     *     <li>Kontrollera om headern börjar med "Bearer ".</li>
     *     <li>Validera JWT-token med {@link JwtUtil}.</li>
     *     <li>Om token är giltig, extrahera användarnamnet och skapa ett {@link UsernamePasswordAuthenticationToken}.</li>
     *     <li>Sätt autentisering i {@link SecurityContextHolder}.</li>
     *     <li>Fortsätt filterkedjan med {@link FilterChain#doFilter(HttpServletRequest, HttpServletResponse)}.</li>
     * </ol>
     * </p>
     *
     * @param request     HTTP-förfrågan.
     * @param response    HTTP-svar.
     * @param filterChain filterkedjan som ska fortsätta exekveras.
     * @throws ServletException om ett servlet-relaterat fel inträffar.
     * @throws IOException      om ett I/O-fel inträffar.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_USER")) // default authority
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
