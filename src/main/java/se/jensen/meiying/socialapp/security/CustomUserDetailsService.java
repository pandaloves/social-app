package se.jensen.meiying.socialapp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.jensen.meiying.socialapp.repository.UserRepository;

/**
 * {@link CustomUserDetailsService} är en implementation av {@link UserDetailsService}
 * som används av Spring Security för att ladda användardetaljer från databasen.
 * <p>
 * Den använder {@link UserRepository} för att hämta en användare baserat på användarnamn
 * och konverterar den till ett {@link CustomUserDetails} objekt som Spring Security kan använda.
 * </p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Skapar en ny instans av {@link CustomUserDetailsService}.
     *
     * @param userRepository repository som används för att hämta användare från databasen.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Laddar en användare baserat på användarnamn.
     * <p>
     * Metoden söker efter användaren i {@link UserRepository}. Om användaren finns,
     * returneras ett {@link CustomUserDetails} objekt. Om användaren inte finns,
     * kastas ett {@link UsernameNotFoundException}.
     * </p>
     *
     * @param username användarnamnet för den användare som ska laddas.
     * @return ett {@link UserDetails} objekt som representerar användaren.
     * @throws UsernameNotFoundException om användaren inte hittas i databasen.
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));
    }
}
