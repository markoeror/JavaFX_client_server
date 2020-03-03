package com.eror.fxclient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class FxclientApplicationTests {

    @Test
    void contextLoads() {

        authorize();
    }

    private void authorize() {
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
    }

}
