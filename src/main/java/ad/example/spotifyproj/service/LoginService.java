package ad.example.spotifyproj.service;

import ad.example.spotifyproj.model.User;
import ad.example.spotifyproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;
    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User getUserByUp(String username,String password)
    {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;
    }
}
