package ad.example.spotifyproj.Model;

public class CreateCustomerRequest {
    private String token;
    private String email;


    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}