package ad.example.spotifyproj.service;

import java.util.List;
import ad.example.spotifyproj.model.Song;
import ad.example.spotifyproj.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class HolidayService {
  private final SongRepository songRepository;
  @Autowired
    public HolidayService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    public List<Song> getSongForHoliday(String holiday)
    {
        List<Song> songList= songRepository.findByHoliday(holiday);
        return songList;
    }
}