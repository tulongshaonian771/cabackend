package ad.example.spotifyproj.Repository;

import ad.example.spotifyproj.Model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationRepository extends JpaRepository<UserLocation,Integer> {
    
    
    @Query("SELECT l.id from UserLocation l where l.address = :address")
    public int findLocationIdByAddress(@Param("address") String address);

    // @Query("SELECT COUNT(*) FROM Location l WHERE l.address = :address")
    // public int countSameLocation(@Param("address") String address);

    boolean existsByAddress(String address);
}

    


