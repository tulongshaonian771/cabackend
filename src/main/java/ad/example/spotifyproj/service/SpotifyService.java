package ad.example.spotifyproj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpotifyService {
    @Value("${spotify.api.trackUrl}")
    private String trackUrl; // "https://api.spotify.com/v1/tracks"
    private final RestTemplate restTemplate;
    private final SpotifyApiService spotifyApiService;
    @Autowired
    public SpotifyService(RestTemplate restTemplate, SpotifyApiService spotifyApiService) {
        this.restTemplate = restTemplate;
        this.spotifyApiService = spotifyApiService;
    }
    public ResponseEntity<String> getTrackDetails(String trackId) {
        String accessToken= spotifyApiService.getAccessToken();;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        ResponseEntity<String> response = restTemplate.exchange(
                trackUrl + "?ids=" + trackId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
        return response;
    }
}