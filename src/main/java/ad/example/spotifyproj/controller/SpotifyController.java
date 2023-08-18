package ad.example.spotifyproj.controller;

import ad.example.spotifyproj.service.SpotifyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyController {
    private final SpotifyApiService spotifyApiService;
    @Autowired
    public SpotifyController(SpotifyApiService spotifyApiService) {
        this.spotifyApiService = spotifyApiService;
    }
    @GetMapping("/get-access-token")
    public String getAccessToken() {
        return spotifyApiService.getAccessToken();
    }
}