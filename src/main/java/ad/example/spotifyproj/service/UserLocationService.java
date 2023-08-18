package ad.example.spotifyproj.service;

import ad.example.spotifyproj.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ad.example.spotifyproj.model.UserLocation;

@Service
public class UserLocationService {
    @Autowired
    private LocationRepository currentLocationRepository;
    public void savecurrentlocation( UserLocation currentLocation){
        currentLocationRepository.save(currentLocation);
    }
    


}
 