package ad.example.spotifyproj.controller;

import ad.example.spotifyproj.model.*;
import ad.example.spotifyproj.service.RecordService;
import ad.example.spotifyproj.service.UserService;
import ad.example.spotifyproj.utility.GeocodingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import  ad.example.spotifyproj.model.LocationData;

@RestController
@RequestMapping("/record")
public class RecordController {
    private final UserService userService;

    private final RecordService recordService;
    @Autowired
    public RecordController(UserService userService, RecordService recordService) {
        this.userService = userService;
        this.recordService = recordService;
    }

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

        songHistory.setSong(song);
        songHistory.setUserLocation(userLocation);
        songHistory.setUser(user);

        recordService.record(song, userLocation, songHistory, user);
        return ResponseEntity.ok("Callback received successfully.");

    }
}
