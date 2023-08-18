package ad.example.spotifyproj.model;

public class SendSong {
    private String uri;
    private String name;
    private String artist;
    private Integer duration;
    private String imageUrl;

    public SendSong(String uri, String name, String artist, Integer duration, String imageUrl) {
        this.uri = uri;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.imageUrl = imageUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}