package ad.example.spotifyproj.Service;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ad.example.spotifyproj.Model.UserLocation;
import ad.example.spotifyproj.Repository.UserLocationRepo;

@Service
public class UserLocationService {
    @Autowired
    private UserLocationRepo currentLocationRepository;
    public void savecurrentlocation( UserLocation currentLocation){
        currentLocationRepository.save(currentLocation);
        
    }
    


}
 