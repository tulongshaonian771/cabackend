package ad.example.spotifyproj.Utility;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class GeocodingUtility {
    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyDiiIF6bHn6O5JnAk7ZZgQX0lOOLLsmIXY";

    public static String getAddressFromCoordinates(double latitude, double longitude) {
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(GOOGLE_MAPS_API_KEY)
                    .build();

            GeocodingResult[] results = GeocodingApi.newRequest(context)
                    .latlng(new com.google.maps.model.LatLng(latitude, longitude))
                    .await();

            if (results != null && results.length > 0) {
                return results[0].formattedAddress;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
