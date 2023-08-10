package ad.example.spotifyproj;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpotifyTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    // Other fields, getters, and setters

    public String getAccessToken() {
        return accessToken;
    }
}
