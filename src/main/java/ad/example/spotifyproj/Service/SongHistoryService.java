package ad.example.spotifyproj.Service;

import ad.example.spotifyproj.Model.SongHistory;
import ad.example.spotifyproj.Model.User;
import ad.example.spotifyproj.Repository.SongHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SongHistoryService {
    private final SongHistoryRepository songHistoryRepository;

    @Autowired
    public SongHistoryService(SongHistoryRepository songHistoryRepository) {
        this.songHistoryRepository = songHistoryRepository;
    }

    public List<String> getAllSongURIs(User user) {
        List<SongHistory> songHistoryList = songHistoryRepository.findSongHistoriesByUser(user);
        // Collect unique song URIs using a Set
        Set<String> songHistorySet = songHistoryList.stream()
                .map(songHistory -> songHistory.getSong().getUri())
                .collect(Collectors.toSet());
        List<String> songHistory = songHistorySet.stream().collect(Collectors.toList());
        return songHistory;
    }
}