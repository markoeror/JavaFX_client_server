package com.eror.server.mappers;

import com.eror.server.dto.UserDTO;
import com.eror.server.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);

    List<UserDTO> listUsersToUsersDTO(Set<User> listUsers);

    List<User> listUsersDTOToUsers(Set<UserDTO> setUsersDTO);
}
