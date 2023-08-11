package ad.example.spotifyproj.Controller;

import ad.example.spotifyproj.Model.User;
import ad.example.spotifyproj.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LogInController {
    private final LoginService loginService;
    @Autowired
    public LogInController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        User user=loginService.getUserByUp(loginForm.getUsername(),loginForm.getPassword());
        if (user != null) {
            return ResponseEntity.ok().build(); // 登录成功
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 登录失败
        }
    }
}

class LoginForm {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


