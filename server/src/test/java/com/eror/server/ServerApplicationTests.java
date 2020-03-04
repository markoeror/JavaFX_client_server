package com.eror.server;

import com.eror.server.dto.RoleDTO;
import com.eror.server.dto.UserDTO;
import com.eror.server.mappers.RoleMapper;
import com.eror.server.mappers.TestMapper;
import com.eror.server.mappers.UserMapper;
import com.eror.server.model.Role;
import com.eror.server.model.User;
import com.eror.server.service.RoleService;
import com.eror.server.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ServerApplicationTests {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {

        checkUsers();
        checkROle();

    }

    private void checkUsers() {
        List<User> users = userService.findAll();

        Assertions.assertNotNull(users);
        assert users.get(0).getName().equals("admin");
        Set<User> userSet = new HashSet<>(users);
        List<UserDTO> userDTOList = userMapper.listUsersToUsersDTO(userSet);
        Assertions.assertNotNull(userDTOList);
        assert userDTOList.get(0).getName().equals("admin");

    }


    private void checkROle() {
        List<Role> roleList = roleService.findAll();
        Assertions.assertNotNull(roleList);
        assert roleList.size() == 2;
        assert roleList.get(0).getId() == (1);
        assert roleList.get(1).getId() == (2);
        Role roleEntity = roleService.find((long) 1);
        Assertions.assertNotNull(roleEntity);
        RoleDTO roleDTO = roleMapper.roletoRoleDTO(roleEntity);
        Assertions.assertNotNull(roleDTO);
        assertEquals(roleDTO.getId(), roleEntity.getId());
        assertEquals(roleDTO.getRoleName(), roleEntity.getName());
        List<RoleDTO> roleDTOS = roleMapper.listRoleDTOs(roleList);
        Assertions.assertNotNull(roleDTOS);
        assertEquals(roleDTOS.get(0).getRoleName(), roleList.get(0).getName());

    }

}
