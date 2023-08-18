package ad.example.spotifyproj.service;

import ad.example.spotifyproj.model.Song;
import ad.example.spotifyproj.model.SongHistory;
import ad.example.spotifyproj.model.User;
import ad.example.spotifyproj.model.UserLocation;
import ad.example.spotifyproj.repository.LocationRepository;
import ad.example.spotifyproj.repository.SongHistoryRepository;
import ad.example.spotifyproj.repository.SongRepository;
import ad.example.spotifyproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
    private final SongRepository songRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private  final SongHistoryRepository songHistoryRepository;
    @Autowired
    public RecordService(SongRepository songRepository, LocationRepository locationRepository, UserRepository userRepository, SongHistoryRepository songHistoryRepository) {
        this.songRepository = songRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.songHistoryRepository = songHistoryRepository;
    }

    public void record(Song song, UserLocation userLocation, SongHistory songHistory, User user) {
        Song existingSong = songRepository.findByUri(song.getUri());
        if (existingSong == null) {
            songRepository.save(song);
        } else {
            song = existingSong;
        }

        UserLocation existingLocation = locationRepository.findByAddress(userLocation.getAddress());
        if (existingLocation == null) {
            locationRepository.save(userLocation);
        } else {
            userLocation = existingLocation;
        }

        songHistory.setSong(song);
        songHistory.setUserLocation(userLocation);
        songHistoryRepository.save(songHistory);
    }

}
