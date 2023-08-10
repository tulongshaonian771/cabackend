package ad.example.spotifyproj.Controller;


import ad.example.spotifyproj.Model.User;
import ad.example.spotifyproj.Service.LocationService;
import ad.example.spotifyproj.Service.PythonService;
import ad.example.spotifyproj.Service.SpotifyService;
import ad.example.spotifyproj.Service.UserService;
import ad.example.spotifyproj.Utility.GeocodingUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping( "/location")
    public ResponseEntity<List<SendSong>> generateSongByLocation(@RequestBody ReceivedLocation location) {
       
        int locationId, timeType, number;
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String address = GeocodingUtility.getAddressFromCoordinates(latitude, longitude);
        User user = userService.findUserByUsername(location.getUsername());
        long userId = user.getId();

//        if(userService.isUserPremium(userId)){
//            //get locationId from database
//             locationId = locationService.findLocationIdByAddress(address);
             timeType = -1;
             number = 24;
//        }
//        else{
//            locationId = locationService.findLocationIdByAddress(address);
//             timeType = -1;
//             number = 12;
//        }
        //this will catch data from python 
       
        List<String> playlist = pythonService.senddatatoPython();
        List<SendSong> SendSongList = new ArrayList<>();
        for (String trackId : playlist) {
            ResponseEntity<String> response = spotifyService.getTrackDetails(trackId);
            String responseBody = response.getBody();
            try {
                JsonNode trackDetails = objectMapper.readTree(responseBody);
                JsonNode track = trackDetails.get("tracks").get(0); // Assuming there's only one track in the "tracks" array

                // 获取歌曲名称
                String songName = track.get("name").asText();

                // 获取第一个艺术家的名称
                String artistName = track.get("artists").get(0).get("name").asText();

                // 获取时长（以毫秒为单位）
                int duration = track.get("duration_ms").asInt();

                SendSong sendSong = new SendSong(trackId, songName, artistName, duration);
                SendSongList.add(sendSong);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(SendSongList);
    }
}
    
class ReceivedLocation{
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

class SendSong{
    private String uri;
    private String name;
    private String artist;
    private Integer duration;

    public SendSong(String uri, String name, String artistName, Integer duration) {
        this.uri = uri;
        this.name = name;
        this.artist = artistName;
        this.duration = duration;
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

    public void setArtist(String artistName) {
        this.artist = artistName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}