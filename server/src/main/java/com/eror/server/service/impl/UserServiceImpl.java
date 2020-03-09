package com.eror.server.service.impl;


import com.eror.server.dto.UserDTO;
import com.eror.server.mappers.UserMapper;
import com.eror.server.model.User;
import com.eror.server.repository.UserRepository;
import com.eror.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO save(User entity) {
        return userMapper.userToUserDTO(userRepository.save(entity));
    }

    @Override
    public UserDTO update(User entity) {
        return userMapper.userToUserDTO(userRepository.save(entity));
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO find(Long id) {
        return userMapper.userToUserDTO(userRepository.findUserById(id));
    }

    @Override
    public List<UserDTO> findAll() {

        return userMapper.listUsersToUsersDTO(userRepository.findAll());
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = this.findByEmail(username);
        if (user == null) {
            return false;
        } else {
            if (password.equals(user.getPassword())) return true;
            else return false;
        }
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        UserDTO userDTOSaved;
        try {
            userDTOSaved = userMapper.userToUserDTO(userMapper.userDTOToUser(userDTO));
            assert userDTOSaved != null;
        } catch (Exception e) {
            throw new EntityNotFoundException("Exception while saving user .");
        }
        return userDTOSaved;
    }

    @Override
    public UserDTO findById(Long id) {
        UserDTO userDTO;
        try {
            userDTO = userMapper.userToUserDTO(userRepository.findUserById(id));
        } catch (Exception e) {
            throw new EntityNotFoundException("Exception while retrieving user .");
        }

        return userDTO;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserDTO> userDTOS;
        try {
            userDTOS = userMapper.listUsersToUsersDTO(userRepository.findAllUsers());
        } catch (Exception e) {
            throw new EntityNotFoundException("Exception while retrieving users .");
        }
        return userDTOS;
    }

    @Override
    public List<UserDTO> deleteInBatch(List<UserDTO> usersDTO) {
        try {
            userRepository.deleteInBatch(userMapper.listUsersDTOToUsers(usersDTO));
        } catch (Exception e) {
            throw new EntityNotFoundException("Exception while deleting users .");
        }

        try {
            return findAllUsers();
        } catch (Exception e) {
            throw new EntityNotFoundException("Exception while retrieving users.");
        }


    }

}
