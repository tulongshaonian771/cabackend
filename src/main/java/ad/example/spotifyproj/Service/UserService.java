package ad.example.spotifyproj.Service;

import ad.example.spotifyproj.Model.User;
import ad.example.spotifyproj.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserPremium(long userId){
        return userRepository.isUserPremium(userId);
    }
    public User findUserByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user)
    {
        userRepository.save(user);
    }

}
