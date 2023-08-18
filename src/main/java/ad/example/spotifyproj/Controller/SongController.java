package ad.example.spotifyproj.Controller;


import ad.example.spotifyproj.Model.User;
import ad.example.spotifyproj.Service.*;
import ad.example.spotifyproj.Utility.GeocodingUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class SongController {

    @Autowired
    private PythonService pythonService;
    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;
    @Autowired
    private SpotifyService spotifyService;
    @Autowired
    private SongHistoryService songHistoryService;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        //this will catch data from python 

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
            //get locationId from database
            number = 12;
        } else {
            number = 6;
        }
        //this will catch data from python

        List<String> playlist = pythonService.senddatatoPython(userId, locationId, timeType, number);
        return getDataFromSpotify(playlist);
    }

    @PostMapping("/public")
    public ResponseEntity<List<SendSong>> generatePublicSong(@RequestBody ReceivedLocation location) {
        //this will catch data from python
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
        //this will catch data from python

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
        //this will catch data from python

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

class ReceivedLocation {
    private double latitude;
    private double longitude;
    private String username;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

class SendSong {
    private String uri;
    private String name;
    private String artist;
    private Integer duration;
    private String imageUrl;

    public SendSong(String uri, String name, String artist, Integer duration, String imageUrl) {
        this.uri = uri;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.imageUrl = imageUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}