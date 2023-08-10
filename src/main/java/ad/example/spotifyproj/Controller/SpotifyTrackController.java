package ad.example.spotifyproj.Controller;

import ad.example.spotifyproj.Service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyTrackController {
    private final SpotifyService spotifyService;
    @Autowired
    public SpotifyTrackController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/get-track-details")
    public ResponseEntity<String> getTrackDetails(String trackId) {
        return spotifyService.getTrackDetails(trackId);
    }
}
