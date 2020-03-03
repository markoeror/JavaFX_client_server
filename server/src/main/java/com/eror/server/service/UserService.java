package com.eror.server.service;


import com.eror.server.dto.UserDTO;
import com.eror.server.generic.GenericService;
import com.eror.server.model.User;

public interface UserService extends GenericService<User> {

    boolean authenticate(String email, String password);

    User findByEmail(String email);

    UserDTO save(UserDTO userDTO);

    UserDTO findById(Long id);
}
