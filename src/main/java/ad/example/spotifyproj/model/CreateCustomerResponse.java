package ad.example.spotifyproj.model;

public class CreateCustomerResponse {
    private String customerId;
    private String message;


    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    // Example constructor
    public CreateCustomerResponse(String customerId, String message) {
        this.customerId = customerId;
        this.message = message;
    }
}