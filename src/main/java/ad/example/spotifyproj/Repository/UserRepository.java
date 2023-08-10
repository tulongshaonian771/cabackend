package ad.example.spotifyproj.Repository;

import ad.example.spotifyproj.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsernameAndPassword(String username,String password);
    public User findByUsername(String username);
    @Query("SELECT u.premium FROM User u WHERE u.id = :userId")
    boolean isUserPremium(@Param("userId") long userId);
}
