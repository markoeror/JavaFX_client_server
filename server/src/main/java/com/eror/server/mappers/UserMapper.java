package com.eror.server.mappers;

import com.eror.server.dto.RoleDTO;
import com.eror.server.dto.UserDTO;
import com.eror.server.model.Role;
import com.eror.server.model.User;
import com.eror.server.service.UserService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {
})

public interface UserMapper {


    @Mapping(source = "user", target = "role", qualifiedByName = "roleDTO")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "role.name", target = "roleName")
    RoleDTO roleToRoleDTO(Role role);

    @Named("roleDTO")
    default RoleDTO userToUserRoleDTO(User user) {
        List<Role> roleList = new ArrayList<>();
        roleList.addAll(user.getRoles());
        Role role = new Role();
        if (!roleList.isEmpty()) {
            role = roleList.get(0);
        }
        RoleMapper roleMapper;

        RoleDTO roleDTO = roleToRoleDTO(role);
        return roleDTO;

    }

    User userDTOToUser(UserDTO userDTO);

    List<UserDTO> listUsersToUsersDTO(List<User> listUsers);

    List<User> listUsersDTOToUsers(List<UserDTO> setUsersDTO);
}
