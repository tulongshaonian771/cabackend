//package ad.example.spotifyproj.Controller;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//
//import ad.example.spotifyproj.Model.UserLocation;
//import ad.example.spotifyproj.Service.UserLocationService;
//import ad.example.spotifyproj.Utility.GoogleMapApi;
//
//@RestController
//@CrossOrigin(origins = "*")
//public class UserLocationController {
//    @Autowired
//private UserLocationService currLocationService;
//
//    @PostMapping("/api/saveLocation")
//    public ResponseEntity<String> saveUserLocation(@RequestBody UserLocation currentLocation) {
//        // Here, you can handle the received userLocation object as needed.
//        // For demonstration purposes, let's just print the coordinates and send a success response.
//
//        double latitude = currentLocation.getLatitude();
//        double longitude = currentLocation.getLongitude();
//        long timestamp=currentLocation.getTimeStamp();
//        String address = GoogleMapApi.getAddressFromCoordinates(latitude, longitude);
//
//        if (address != null) {
//            System.out.println("Address: " + address);
//        } else {
//            System.out.println("Unable to find address for the given coordinates.");
//        }
//        Date date = new Date(timestamp);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateTime =dateFormat.format(timestamp);
//
//
//
//        System.out.println("Received location - Latitude: " + latitude + ", Longitude: " + longitude);
//        System.out.println("Time Received: " + dateTime);
//        UserLocation currLocationEntity = new UserLocation();
//        currLocationEntity.setLatitude(latitude);
//        currLocationEntity.setLongitude(longitude);
//        currLocationEntity.setTimeStamp(timestamp);
//        currLocationEntity.setAddress(address);
//        currLocationEntity.setDateTime(dateTime);
//
//        // Your logic to save the location data to the database or perform other actions.
//           currLocationService.savecurrentlocation(currLocationEntity);
//            System.out.println("Save to database");
//        // Send a success response to the client.
//        return ResponseEntity.ok("Current Location saved successfully.");
//
//    }
//}
//
//
//
