package ad.example.spotifyproj.service;

import ad.example.spotifyproj.model.UserLocation;
import ad.example.spotifyproj.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    public void saveLocation(UserLocation location) {
        if (!locationRepository.existsByAddress(location.getAddress())) {
            locationRepository.save(location);
        }
    }
    public long findLocationIdByAddress(String address) {
        var id = locationRepository.findLocationIdByAddress(address);
        return id <= 0 ? -1 : id;
    }
}
