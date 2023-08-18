package ad.example.spotifyproj.controller;


import ad.example.spotifyproj.model.ReceivedLocation;
import ad.example.spotifyproj.model.SendSong;
import ad.example.spotifyproj.model.User;
import ad.example.spotifyproj.service.*;
import ad.example.spotifyproj.utility.GeocodingUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class SongController {
    private final PythonService pythonService;
    private final UserService userService;
    private final LocationService locationService;
    private final SpotifyService spotifyService;
    private final SongHistoryService songHistoryService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    public SongController(PythonService pythonService, UserService userService, LocationService locationService, SpotifyService spotifyService, SongHistoryService songHistoryService) {
        this.pythonService = pythonService;
        this.userService = userService;
        this.locationService = locationService;
        this.spotifyService = spotifyService;
        this.songHistoryService = songHistoryService;
    }

    @PostMapping("/location")
    public ResponseEntity<List<SendSong>> generateSongByLocation(@RequestBody ReceivedLocation location) {
        int timeType, number;
        int locationId = -1;
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String address = GeocodingUtility.getAddressFromCoordinates(latitude, longitude);
        User user = userService.findUserByUsername(location.getUsername());
        long userId = user.getId();
        try {
            locationId = Math.toIntExact(locationService.findLocationIdByAddress(address));
        } catch (Exception ignore) {
        }
        timeType = -1;
        if (userService.isUserPremium(userId)) {
            number = 12;
        } else {
            number = 6;
        }
        List<String> playlist = pythonService.senddatatoPython(userId, locationId, timeType, number);
        return getDataFromSpotify(playlist);
    }

    @PostMapping("/time")
    public ResponseEntity<List<SendSong>> generateSongByTime(@RequestBody ReceivedLocation location) {
        int locationId, timeType, number;
        User user = userService.findUserByUsername(location.getUsername());
        LocalDateTime currentDateTime = LocalDateTime.now();
        timeType = RecordController.getTimeType(currentDateTime);
        long userId = user.getId();
        locationId = -1;
        if (userService.isUserPremium(userId)) {
            number = 12;
        } else {
            number = 6;
        }
        List<String> playlist = pythonService.senddatatoPython(userId, locationId, timeType, number);
        return getDataFromSpotify(playlist);
    }

    @PostMapping("/public")
    public ResponseEntity<List<SendSong>> generatePublicSong(@RequestBody ReceivedLocation location) {
        List<String> playlist = pythonService.senddatatoPython(0, -1, -1, 6);
        return getDataFromSpotify(playlist);
    }

    @GetMapping("/publicForAndroid")
    public ResponseEntity<List<SendSong>> generatePublicSongForAndroid() {
        int locationId, timeType, number;
        long userId = 0;
        locationId = -1;
        timeType = -1;
        number = 18;
        List<String> playlist = pythonService.senddatatoPython(userId, locationId, timeType, number);
        return getDataFromSpotify(playlist);
    }

    @PostMapping("/after")
    public ResponseEntity<List<SendSong>> generateSongAfterLogin(@RequestBody ReceivedLocation location) {
        int locationId, timeType, number;
        User user = userService.findUserByUsername(location.getUsername());
        long userId = user.getId();
        timeType = -1;
        locationId = -1;
        if (userService.isUserPremium(userId)) {
            number = 12;
        } else {
            number = 6;
        }
        List<String> playlist = pythonService.senddatatoPython(userId, locationId, timeType, number);
        return getDataFromSpotify(playlist);
    }

    @PostMapping("/history")
    public ResponseEntity<List<SendSong>> generateHistory(@RequestBody ReceivedLocation location) {
        User user = userService.findUserByUsername(location.getUsername());
        List<String> playlist = songHistoryService.getAllSongURIs(user);
        return getDataFromSpotify(playlist);
    }

    public ResponseEntity<List<SendSong>> getDataFromSpotify(List<String> playlist) {
        List<SendSong> SendSongList = new ArrayList<>();
        StringBuilder tracks = new StringBuilder();
        for (String trackId : playlist) {
            tracks.append(",").append(trackId);
        }
        tracks = new StringBuilder(tracks.substring(1));
        try {
            ResponseEntity<String> response = spotifyService.getTrackDetails(tracks.toString());
            String responseBody = response.getBody();
            JsonNode trackDetails = objectMapper.readTree(responseBody).get("tracks");
            for (int i = 0; i < trackDetails.size(); i++) {
                JsonNode track = trackDetails.get(i);
                String trackId = playlist.get(i);
                String songName = track.get("name").asText();
                String artistName = track.get("artists").get(0).get("name").asText();
                int duration = track.get("duration_ms").asInt();
                String imageUrl = track.get("album").get("images").get(0).get("url").asText();
                SendSong sendSong = new SendSong(trackId, songName, artistName, duration, imageUrl);
                SendSongList.add(sendSong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(SendSongList);
    }
}