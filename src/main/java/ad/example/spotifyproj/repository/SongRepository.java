package ad.example.spotifyproj.repository;

import ad.example.spotifyproj.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song,Long> {
    public List<Song> findByHoliday(String holiday);
    public  Song findByUri(String uri);
}
