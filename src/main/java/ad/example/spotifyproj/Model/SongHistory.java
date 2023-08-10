package ad.example.spotifyproj.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SongHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int timeType;  //Moring for 0, Noon for 1, Evening for 2 ...

    private int timeOfPlay;  

    @ManyToOne(fetch = FetchType.LAZY) // Many-to-One relationship with UserLocation
    @JoinColumn(name = "location_id") // This is the foreign key column in the SongHistory table
    private UserLocation userLocation;

    @ManyToOne(fetch = FetchType.LAZY) // Many-to-One relationship with Song
    @JoinColumn(name = "song_id") // This is the foreign key column in the SongHistory table
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User user;
  
}
