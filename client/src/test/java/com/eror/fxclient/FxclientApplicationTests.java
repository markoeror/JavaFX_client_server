package com.eror.fxclient;

import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;
import com.eror.fxclient.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
class FxclientApplicationTests {
    String token;

    @Test
    void contextLoads() {

        token = authorize();
        Assertions.assertNotNull(token);
        User user = getUser();
        updateUser(user);
    }


    private String authorize() {
        String url = "http://localhost:8082/api/auth/signin";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "adminadmin");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getStatusCode());
        HttpStatus statusCode = response.getStatusCode();
        Assertions.assertNotNull(statusCode);
        assert statusCode.equals(HttpStatus.OK);
        System.out.println("Status code is: " + statusCode);
        JsonParserFactory factory = JsonParserFactory.getInstance();
        JSONParser parser = factory.newJsonParser();
        Map jsonMap = parser.parseJson(Objects.requireNonNull(response.getBody()));
        String token = jsonMap.get("accessToken").toString();
        System.out.println("Token is: " + token);
        String tokenWithBearer = "Bearer " + token;
        System.out.println(tokenWithBearer);
        return tokenWithBearer;
    }

    private User getUser() {
        String url = "http://localhost:8082/api/user/getUser";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        User user = new User();
        user.setId(7);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity(url, entity, User.class);
        User user1 = response.getBody();
        return user1;
    }

    private void updateUser(User user) {
        String url = "http://localhost:8082/api/user/saveUser";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        HttpEntity<User> entity = new HttpEntity<>(user, headers);
// send POST request
        ResponseEntity<User> response = restTemplate.postForEntity(url, entity, User.class);
        HttpStatus status = response.getStatusCode();
        assert status.equals(HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());

    }

}
