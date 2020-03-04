package com.eror.fxclient.controller;


import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;
import com.eror.fxclient.config.StageManager;
import com.eror.fxclient.model.User;
import com.eror.fxclient.view.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;


@Controller
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label lblLogin;

//    @Autowired
//    private UserService userService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void login(ActionEvent event) throws IOException {
        User user = new User();
        user.setUsername(getUsername());
        user.setPassword(getPassword());
//        User dummyuserFromApi= getDummyUser();
        getTokenAuthorisation(getUsername(), getPassword());


    }

    private HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    private Object getTokenAuthorisation(String username, String password) throws IOException {
        try {
            String url = "http://localhost:8082/api/auth/signin";
            RestTemplate restTemplate = new RestTemplate();
// create headers
            HttpHeaders headers = new HttpHeaders();
// set `content-type` header
            headers.setContentType(MediaType.APPLICATION_JSON);
// set `accept` header
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

// request body parameters
            Map<String, Object> map = new HashMap<>();
            map.put("username", username);
            map.put("password", password);
// build the request
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
// send POST request
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
// check response
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Request Successful");
                System.out.println(response.getBody());

                // parsing JSON String from api

                JsonParserFactory factory = JsonParserFactory.getInstance();
                JSONParser parser = factory.newJsonParser();
                Map jsonMap = parser.parseJson(Objects.requireNonNull(response.getBody()));
                System.out.println(jsonMap);
                String usernameJson = jsonMap.get("username").toString();
                System.out.println("username is: " + usernameJson);
                String token = jsonMap.get("accessToken").toString();
                System.out.println("Token is: " + token);
                ArrayList authorityList = ((ArrayList) jsonMap.get("authorities"));
                Map authMap = (Map) authorityList.get(0);
                String auth = (String) authMap.get("authority");
                System.out.println("Authority is: " + auth);
                lblLogin.setText("Login Success.");

                stageManager.setUser(jsonMap);
                stageManager.switchScene(FxmlView.USER);
            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
                lblLogin.setText("Login Failed.");
            }
            return response;
        } catch (Exception ex) {
            System.out.println("Request Failed");
            System.out.println(ex);
            return null;
        }
    }

    private User getDummyUser() throws IOException {

        try {
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<User> response =
                    restTemplate.getForEntity(
                            "http://localhost:8082/getUser",
                            User.class);
            User user = response.getBody();
            assert user != null;
            System.out.println(user.toString());

            return user;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }

    }

    public String getPassword() {
        return password.getText();
    }

    public String getUsername() {
        return username.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
