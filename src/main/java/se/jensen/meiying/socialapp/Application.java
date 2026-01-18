package se.jensen.meiying.socialapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * {@link Application} är ingångspunkten för SocialApp-backend.
 * <p>
 * Klassen konfigurerar och startar Spring Boot-applikationen.
 * Den används för att starta hela backend-systemet och ladda alla konfigurationer,
 * komponenter och beans.
 * </p>
 */
@SpringBootApplication
public class Application {

    /**
     * Startar Spring Boot-applikationen.
     * <p>
     * Huvudmetoden anropas när applikationen körs, vilket triggar Spring Boot
     * att konfigurera hela applikationskonteksten och starta inbäddad server.
     * </p>
     *
     * @param args eventuella kommandoradsargument som kan skickas in vid start.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
