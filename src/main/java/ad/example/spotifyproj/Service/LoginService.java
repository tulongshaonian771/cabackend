package ad.example.spotifyproj.Service;

import ad.example.spotifyproj.Model.User;
import ad.example.spotifyproj.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
