package ad.example.spotifyproj.Service;
import ad.example.spotifyproj.Model.UserLocation;
import ad.example.spotifyproj.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
@Autowired
    private LocationRepository locationRepository;

 


    public void saveLocation( UserLocation location){
        if (!locationRepository.existsByAddress(location.getAddress()))
        {
             locationRepository.save(location);
        }       
    }

    public int findLocationIdByAddress(String address){
        return locationRepository.findLocationIdByAddress(address);
    }

}
