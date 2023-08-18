package ad.example.spotifyproj.repository;

import ad.example.spotifyproj.model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<UserLocation,Long> {
    @Query("SELECT l.id from UserLocation l where l.address = :address")
    public int findLocationIdByAddress(@Param("address") String address);
    public UserLocation findByAddress(String address);
    boolean existsByAddress(String address);
}

    


