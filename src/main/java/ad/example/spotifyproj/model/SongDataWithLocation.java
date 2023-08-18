package ad.example.spotifyproj.model;

import ad.example.spotifyproj.controller.RecordController;

public class SongDataWithLocation {
    private String songURI;
    private LocationData location;

    private String username;

    public String getSongURI() {
        return songURI;
    }

    public void setSongURI(String songURI) {
        this.songURI = songURI;
    }

    public LocationData getLocation() {
        return location;
    }

    public void setLocation(LocationData location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}