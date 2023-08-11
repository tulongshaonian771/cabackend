package ad.example.spotifyproj.Repository;

import ad.example.spotifyproj.Model.SongHistory;
import ad.example.spotifyproj.Model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongHistoryRepository extends JpaRepository<SongHistory,Long> {


}
