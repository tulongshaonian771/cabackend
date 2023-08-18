package ad.example.spotifyproj.model;

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
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double latitude;
    private double longitude;
    private String address;
    @OneToMany(mappedBy = "userLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SongHistory> songHistoryList = new ArrayList<>();
    public void addSongHistory(SongHistory songHistory) {
        songHistoryList.add(songHistory);
        songHistory.setUserLocation(this);
    }

    public void removeSongHistory(SongHistory songHistory) {
        songHistoryList.remove(songHistory);
        songHistory.setUserLocation(null);
    }
     
}
