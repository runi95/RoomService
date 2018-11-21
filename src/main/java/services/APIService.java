package services;

import controllers.RoomController;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

@ApplicationScoped
public class APIService {


    @Inject
    @ConfigurationValue("api.auth.host")
    private String API_AUTH_HOST;

    @Inject
    @ConfigurationValue("api.auth.endpoints.verify")
    private String API_AUTH_ENDPOINTS_VERIFY;

    private static final Logger LOG = Logger.getLogger(APIService.class.getName());

    public boolean verifyToken(String email, String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", USER_AGENT);
        headers.put("email", email);
        headers.put("token", token);

        String response = null;

        try {
            response = sendGetRequest(API_AUTH_HOST + API_AUTH_ENDPOINTS_VERIFY, headers);
        } catch (IOException e) {
            LOG.warning("caught an exception when sending verify request to API");

            // e.printStackTrace();
        }

        if (response == null) {
            return false;
        } else {
            if (response.equals("true")) {
                return true;
            } else {
                return false;
            }
        }
    }

    public String sendGetRequest(String urlAsString, Map<String, String> headers) throws IOException {
        URL url = new URL(urlAsString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        // Set all headers
        headers.keySet().forEach((key) -> con.setRequestProperty(key, headers.get(key)));

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else { // Fail
            LOG.warning("GET request to " + urlAsString + " failed with response code " + responseCode);
        }

        return null;
    }
}
