package ad.example.spotifyproj.Service;

import ad.example.spotifyproj.Model.Song;
import ad.example.spotifyproj.Model.SongHistory;
import ad.example.spotifyproj.Model.User;
import ad.example.spotifyproj.Model.UserLocation;
import ad.example.spotifyproj.Repository.LocationRepository;
import ad.example.spotifyproj.Repository.SongHistoryRepository;
import ad.example.spotifyproj.Repository.SongRepository;
import ad.example.spotifyproj.Repository.UserRepository;
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
            song = existingSong; // Use the existing song
        }

        UserLocation existingLocation = locationRepository.findByAddress(userLocation.getAddress());
        if (existingLocation == null) {
            locationRepository.save(userLocation);
        } else {
            userLocation = existingLocation; // Use the existing location
        }

        songHistory.setSong(song);
        songHistory.setUserLocation(userLocation);
        songHistoryRepository.save(songHistory);
    }

}
