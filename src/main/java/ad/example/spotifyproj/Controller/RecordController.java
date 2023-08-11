package ad.example.spotifyproj.Controller;

import ad.example.spotifyproj.Model.Song;
import ad.example.spotifyproj.Model.SongHistory;
import ad.example.spotifyproj.Model.User;
import ad.example.spotifyproj.Model.UserLocation;
import ad.example.spotifyproj.Service.RecordService;
import ad.example.spotifyproj.Service.UserService;
import ad.example.spotifyproj.Utility.GeocodingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/record")
public class RecordController {
    private final UserService userService;

    private final RecordService recordService;

    public RecordController(UserService userService, RecordService recordService) {
        this.userService = userService;
        this.recordService = recordService;
    }

    @Autowired
    public static int getTimeType(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();

        if (time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(LocalTime.of(6, 0))) {
            return 0;
        } else if (time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.NOON)) {
            return 1;
        } else if (time.isAfter(LocalTime.NOON) && time.isBefore(LocalTime.of(18, 0))) {
            return 2;
        } else {
            return 3;
        }
    }

    @PostMapping
    public ResponseEntity<String> handleCallback(@RequestBody SongDataWithLocation songData) {
        String songURI = songData.getSongURI();
        LocationData location = songData.getLocation();
        String username = songData.getUsername();
        User user = userService.findUserByUsername(username);

        LocalDateTime currentDateTime = LocalDateTime.now();
        int timType = getTimeType(currentDateTime);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String address = GeocodingUtility.getAddressFromCoordinates(latitude, longitude);

        // Create and set up entities
        Song song = new Song();
        song.setUri(songURI);
        song.setHoliday("normal");

        UserLocation userLocation = new UserLocation();
        userLocation.setLatitude(latitude);
        userLocation.setLongitude(longitude);
        userLocation.setAddress(address);

        SongHistory songHistory = new SongHistory();
        songHistory.setTimeType(timType);
        songHistory.setTimeOfPlay(currentDateTime);

        // Add associations
        songHistory.setSong(song);
        songHistory.setUserLocation(userLocation);
        songHistory.setUser(user);

        // Pass entities to the service for saving
        recordService.record(song, userLocation, songHistory, user);


        return ResponseEntity.ok("Callback received successfully.");

    }
    public static class SongDataWithLocation {
        private String songURI;
        private LocationData location;

        private String username;

        public String getSongURI() {
            return songURI;
        }

        public void setSongURI(String songURI) {
            this.songURI = songURI;
        }

        public LocationData getLocation() {
            return location;
        }

        public void setLocation(LocationData location) {
            this.location = location;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
    public static class LocationData {
        private double latitude;
        private double longitude;

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
    }
}
