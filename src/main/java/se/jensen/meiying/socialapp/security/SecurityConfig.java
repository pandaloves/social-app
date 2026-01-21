package se.jensen.meiying.socialapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * {@link SecurityConfig} konfigurerar Spring Security för projektet.
 * <p>
 * Klassen hanterar autentisering, JWT-filter, CORS-konfiguration,
 * och vilka endpoints som är tillgängliga utan autentisering.
 * </p>
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Skapar en ny instans av {@link SecurityConfig}.
     *
     * @param jwtAuthFilter JWT-autentiseringsfilter som används för att validera tokens.
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Skapar en {@link PasswordEncoder}-instans som används för att kryptera lösenord.
     * <p>
     * Här används {@link BCryptPasswordEncoder}.
     * </p>
     *
     * @return en {@link PasswordEncoder}-instans.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Konfigurerar säkerhetsfilterkedjan för HTTP-förfrågningar.
     * <p>
     * - Tillåter CORS-konfiguration via {@link #corsConfigurationSource()}.
     * - Inaktiverar CSRF.
     * - Definierar offentliga endpoints som inte kräver autentisering.
     * - Alla andra requests kräver autentisering.
     * - Lägger till {@link JwtAuthenticationFilter} före {@link UsernamePasswordAuthenticationFilter}.
     * </p>
     *
     * @param http {@link HttpSecurity}-instansen som ska konfigureras.
     * @return en byggd {@link SecurityFilterChain}-instans.
     * @throws Exception om ett säkerhetsrelaterat fel inträffar vid byggandet av kedjan.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/users/login",
                                "/users/",
                                "/users",
                                "/users/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/posts",
                                "/posts/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Konfigurerar CORS (Cross-Origin Resource Sharing) för projektet.
     * <p>
     * Tillåter specifika origins, HTTP-metoder, headers och exponering av Authorization-header.
     * </p>
     *
     * @return en {@link CorsConfigurationSource}-instans som används av Spring Security.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
