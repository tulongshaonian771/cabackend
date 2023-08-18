package ad.example.spotifyproj.service;

import ad.example.spotifyproj.model.User;
import ad.example.spotifyproj.repository.UserRepository;
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
