package ad.example.spotifyproj.repository;

import ad.example.spotifyproj.model.SongHistory;
import ad.example.spotifyproj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongHistoryRepository extends JpaRepository<SongHistory,Long> {
    public List<SongHistory> findSongHistoriesByUser(User user);
}
