package ad.example.spotifyproj.Repository;

import ad.example.spotifyproj.Model.SongHistory;
import ad.example.spotifyproj.Model.User;
import ad.example.spotifyproj.Model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongHistoryRepository extends JpaRepository<SongHistory,Long> {

    public List<SongHistory> findSongHistoriesByUser(User user);
}
