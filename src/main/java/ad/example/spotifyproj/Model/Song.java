package ad.example.spotifyproj.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uri;

    private String holiday;
   

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SongHistory> songHistoryList;

  
    // Helper method to manage bidirectional relationship with SongHistory
    public void addSongHistory(SongHistory songHistory) {
        songHistoryList.add(songHistory);
        songHistory.setSong(this);
    }

    public void removeSongHistory(SongHistory songHistory) {
        songHistoryList.remove(songHistory);
        songHistory.setSong(null);
    }
    
}
   