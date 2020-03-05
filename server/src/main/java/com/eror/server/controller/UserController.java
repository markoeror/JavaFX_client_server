package com.eror.server.controller;

import com.eror.server.dto.UserDTO;
import com.eror.server.mappers.UserMapper;
import com.eror.server.model.User;
import com.eror.server.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user/")
public class UserController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // Method for retrieving User list
    @GetMapping("getUsers")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        try {
            List<UserDTO> userDTOList = userService.findAll();
            logger.debug("Users list retrieved.");
            return new ResponseEntity<>(userDTOList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while retrieving Users list");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Method for retrieving specific User
    @PostMapping("getUser")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUser(@RequestBody User user) {
        try {
            Long id = user.getId();
            UserDTO userDTO = userService.findById(id);

            logger.debug("User details retrieved.");
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while retrieving User details");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Method for saving User
    @PostMapping(value = "saveUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO userDto = userService.save(userDTO);
            logger.debug("User saved.");
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving User");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Method for saving User
    @PostMapping(value = "deleteUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> deleteUsers(@RequestBody List<UserDTO> usersDTO) {
        try {
            List<UserDTO> userDTOList = userService.deleteInBatch(usersDTO);
            logger.debug("Users deleted.");
            return new ResponseEntity<>(userDTOList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting Users");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
