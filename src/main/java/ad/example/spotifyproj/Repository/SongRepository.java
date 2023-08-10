package ad.example.spotifyproj.Repository;

import ad.example.spotifyproj.Model.Song;
import ad.example.spotifyproj.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,Long> {
    public List<Song> findByHoliday(String holiday);
}
