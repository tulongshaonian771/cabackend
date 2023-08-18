package ad.example.spotifyproj.config;
import ad.example.spotifyproj.service.SpotifyApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotifyConfig {

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;

    public SpotifyApiService spotifyApiService() {
        return new SpotifyApiService();
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    // Getters for clientId and clientSecret
}
