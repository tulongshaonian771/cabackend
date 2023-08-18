package ad.example.spotifyproj.controller;

import ad.example.spotifyproj.model.Information;
import ad.example.spotifyproj.model.User;
import ad.example.spotifyproj.service.UserService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import ad.example.spotifyproj.model.CreateCustomerRequest;
import ad.example.spotifyproj.model.CreateCustomerResponse;
import java.io.IOException;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class StripeController {

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;

    @Value("${stripe.successUrl}")
    private String stripeSuccessUrl;

    @Value("${stripe.cancelUrl}")
    private String stripeCancelUrl;

    @Value("${stripe.publishableKey}")
    private String stripePublishableKey;

    private final UserService userService;
    @Autowired
    public StripeController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create-subscription")
    public ResponseEntity<?> createSubscriptionSession(@RequestBody Information information) {
        User user = userService.findUserByUsername(information.getUsername());
        try {
            System.out.println("Received a request to create a subscription session for customer: ");
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://api.stripe.com/v1/checkout/sessions");
            httpPost.setHeader("Authorization", "Bearer " + stripeSecretKey);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            String requestBody = "payment_method_types[]=card"
                    + "&mode=subscription"
                    + "&success_url=" + stripeSuccessUrl
                    + "&cancel_url=" + stripeCancelUrl
                    + "&line_items[0][price]=price_1NbDxSGkAJALrYglePAIWB7B"
                    + "&line_items[0][quantity]=1";

            httpPost.setEntity(new StringEntity(requestBody));
            System.out.println("Request body: " + requestBody);
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String responseString = EntityUtils.toString(response.getEntity());
                String sessionId = parseSessionIdFromResponse(responseString);
                user.setPremium(true);
                userService.saveUser(user);
                return ResponseEntity.ok(sessionId);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to create subscription session.");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    private String parseSessionIdFromResponse(String responseString) {
        try {
            JsonObject responseObject = JsonParser.parseString(responseString).getAsJsonObject();
            return responseObject.get("id").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/create-customer")
    public ResponseEntity<CreateCustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
        Stripe.apiKey = stripeSecretKey;

        try {
            CustomerCreateParams params = CustomerCreateParams.builder()
                    .setEmail(request.getEmail())
                    .setSource(request.getToken())
                    .build();
            Customer customer = Customer.create(params);
            CreateCustomerResponse response = new CreateCustomerResponse(customer.getId(), "Customer created successfully");
            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            System.out.println("Stripe API Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create customer: " + e.getMessage());
        }
    }
}