package ad.example.spotifyproj.Service;


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

    public List<String> senddatatoPython(long userId, int locationId, int timeType, int number) {
        String url = "http://localhost:5000/model1?userid=" + userId + "&locationid=" +
                locationId + "&timetype=" + timeType + "&number=" + number;
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        // Check response status code and body if necessary
        if (response.getStatusCode().value() == 200) {
            return response.getBody();
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCode().value());
            return null;
        }
    }

    public List<String> senddatatoPython() {
    String uri = "1BxfuPKGuaTgP7aM0Bbdwr";
    List<String> uriList = new ArrayList<>();
    uriList.add(uri);
        return uriList;
    }
}
