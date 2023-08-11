package ad.example.spotifyproj.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ad.example.spotifyproj.Model.Song;
import ad.example.spotifyproj.Service.SpotifyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ad.example.spotifyproj.Service.HolidayService;



@RestController
@RequestMapping("/holidays")
@CrossOrigin(origins = "http://localhost:3000")
public class HolidayController {
    private final HolidayService holidayService;
    private final SpotifyService spotifyService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    public HolidayController(HolidayService holidayService, SpotifyService spotifyService) {
        this.holidayService = holidayService;
        this.spotifyService = spotifyService;
    }

    @GetMapping
    public ResponseEntity<List<Song>> getSongOfHoliday() {
        LocalDate currentDate = LocalDate.now();
        if (isFestival(currentDate, 8, 9)) {
            // Singapore National Day
            List<Song> songList = holidayService.getSongForHoliday("Singapore National Day");
            return new ResponseEntity<>(songList, HttpStatus.OK);
        } else if (isFestival(currentDate, 8, 11)) {
            // new year
            List<Song> songList = holidayService.getSongForHoliday("New Year's Day");
            return new ResponseEntity<>(songList, HttpStatus.OK);
        } else if (isFestival(currentDate, 1, 21, 2, 20)) {
            // Chinese New Year
            List<Song> songList = holidayService.getSongForHoliday("Chinese New Year");
            return new ResponseEntity<>(songList, HttpStatus.OK);
        } else if (isFestival(currentDate, 8, 8)) {
            // Christmas Day
            List<Song> songList = holidayService.getSongForHoliday("Christmas Day");
            return new ResponseEntity<>(songList, HttpStatus.OK);
        } else if (isFestival(currentDate, 4, 22)) {
            // Hari Raya Puasa
            List<Song> songList = holidayService.getSongForHoliday("Hari Raya Puasa");
            return new ResponseEntity<>(songList, HttpStatus.OK);
        } else if (isFestival(currentDate, 12, 12)) {
            // Deepavali
            List<Song> songList = holidayService.getSongForHoliday("Deepavali");
            return new ResponseEntity<>(songList, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/android")
    public ResponseEntity<List<SendSong>> getSongOfHolidayForAndroid() {
        LocalDate currentDate = LocalDate.now();
        if (isFestival(currentDate, 8, 9)) {
            // Singapore National Day
            List<Song> songList = holidayService.getSongForHoliday("Singapore National Day");
            List<SendSong> sendSongList = getSendSong(songList);
            return new ResponseEntity<>(sendSongList, HttpStatus.OK);
        } else if (isFestival(currentDate, 8, 11)) {
            // new year
            List<Song> songList = holidayService.getSongForHoliday("New Year's Day");
            List<SendSong> sendSongList = getSendSong(songList);
            return new ResponseEntity<>(sendSongList, HttpStatus.OK);
        } else if (isFestival(currentDate, 1, 21, 2, 20)) {
            // Chinese New Year
            List<Song> songList = holidayService.getSongForHoliday("Chinese New Year");
            List<SendSong> sendSongList = getSendSong(songList);
            return new ResponseEntity<>(sendSongList, HttpStatus.OK);
        } else if (isFestival(currentDate, 8, 8)) {
            // Christmas Day
            List<Song> songList = holidayService.getSongForHoliday("Christmas Day");
            List<SendSong> sendSongList = getSendSong(songList);
            return new ResponseEntity<>(sendSongList, HttpStatus.OK);
        } else if (isFestival(currentDate, 4, 22)) {
            // Hari Raya Puasa
            List<Song> songList = holidayService.getSongForHoliday("Hari Raya Puasa");
            List<SendSong> sendSongList = getSendSong(songList);
            return new ResponseEntity<>(sendSongList, HttpStatus.OK);
        } else if (isFestival(currentDate, 12, 12)) {
            // Deepavali
            List<Song> songList = holidayService.getSongForHoliday("Deepavali");
            List<SendSong> sendSongList = getSendSong(songList);
            return new ResponseEntity<>(sendSongList, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Determine if the date is within the specified holiday rangeDetermine if the date is within the specified holiday range
    private static boolean isFestival(LocalDate currentDate, int startMonth, int startDay, int endMonth, int endDay) {
        LocalDate startDate = LocalDate.of(currentDate.getYear(), startMonth, startDay);
        LocalDate endDate = LocalDate.of(currentDate.getYear(), endMonth, endDay);
        return currentDate.isAfter(startDate) && currentDate.isBefore(endDate.plusDays(1));
    }

    // Determine if the date falls on a specified holiday
    private static boolean isFestival(LocalDate currentDate, int month, int day) {
        LocalDate festivalDate = LocalDate.of(currentDate.getYear(), month, day);
        return currentDate.equals(festivalDate);
    }

    private List<SendSong> getSendSong(List<Song> songList) {
        List<SendSong> SendSongList = new ArrayList<>();
        for (Song song : songList) {
            String trackId = song.getUri();
            ResponseEntity<String> response = spotifyService.getTrackDetails(trackId);
            String responseBody = response.getBody();
            try {
                JsonNode trackDetails = objectMapper.readTree(responseBody);
                JsonNode track = trackDetails.get("tracks").get(0);

                String songName = track.get("name").asText();

                String artistName = track.get("artists").get(0).get("name").asText();

                int duration = track.get("duration_ms").asInt();

                String imageUrl = track.get("album").get("images").get(0).get("url").asText();

                SendSong sendSong = new SendSong(trackId, songName, artistName, duration, imageUrl);
                SendSongList.add(sendSong);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return SendSongList;
    }
}

