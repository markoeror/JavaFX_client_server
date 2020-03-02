package com.eror.server.controller;

import com.eror.server.model.User;
import com.eror.server.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("getUser")
    public ResponseEntity<User> getUser() {
        try {
            User user = new User();
            user.setName("Marko");
            user.setUsername("eror");
            user.setPassword("Eror");
            logger.debug("User details retrieved.");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while retrieving User details");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // Method for saving User
    @PostMapping(value = "saveUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        try {
            userService.save(user);
            logger.debug("User saved.");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while retrieving User details");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
