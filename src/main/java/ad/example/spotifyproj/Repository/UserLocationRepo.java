package ad.example.spotifyproj.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ad.example.spotifyproj.Model.UserLocation;

@Repository
public interface UserLocationRepo extends JpaRepository<UserLocation,Long> {
    
}
