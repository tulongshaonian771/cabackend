package ad.example.spotifyproj.Service;

import ad.example.spotifyproj.Model.User;
import ad.example.spotifyproj.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    @Autowired
    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username)
    {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
