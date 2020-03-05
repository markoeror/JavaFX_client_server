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
import java.util.HashSet;
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
        return roleToRoleDTO(role);
    }

    @Mapping(source = "userDTO", target = "roles", qualifiedByName = "rolesUser")
    User userDTOToUser(UserDTO userDTO);


    @Mapping(source = "roleName", target = "name")
    Role roleDTOtoRole(RoleDTO roleDTO);

    @Named("rolesUser")
    default Set<Role> userDTOToUserRoles(UserDTO userDTO) {
        RoleDTO roleDTO = userDTO.getRole();
        Role role = roleDTOtoRole(roleDTO);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        return roleSet;
    }

    List<UserDTO> listUsersToUsersDTO(List<User> listUsers);

    List<User> listUsersDTOToUsers(List<UserDTO> setUsersDTO);
}
