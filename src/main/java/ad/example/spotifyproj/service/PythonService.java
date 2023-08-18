package ad.example.spotifyproj.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PythonService {
    @Autowired
    private RestTemplate restTemplate;
    public List<String> senddatatoPython(long userId, long locationId, int timeType, int number) {
        String url = "http://localhost:5000/recommend?user_id=" + userId + "&location=" +
                locationId + "&timetype=" + timeType + "&num=" + number;
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        if (response.getStatusCode().value() == 200) {
            return response.getBody();
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCode().value());
            return null;
        }
    }
    public List<String> senddatatoPython() {
    String uri = "1BxfuPKGuaTgP7aM0Bbdwr";
        String uri1 = "5oL7vOxCz2eJKAX6G4Yluh";
    List<String> uriList = new ArrayList<>();
    uriList.add(uri);
    uriList.add(uri1);
        return uriList;
    }
}
