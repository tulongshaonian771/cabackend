package ad.example.spotifyproj.Repository;

import ad.example.spotifyproj.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsernameAndPassword(String username,String password);
    public User findByUsername(String username);
}
