package ad.example.spotifyproj.controller;

import ad.example.spotifyproj.model.LoginForm;
import ad.example.spotifyproj.model.User;
import ad.example.spotifyproj.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
