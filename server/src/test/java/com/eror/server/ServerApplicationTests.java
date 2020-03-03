package com.eror.server;

import com.eror.server.dto.RoleDTO;
import com.eror.server.mappers.RoleMapper;
import com.eror.server.mappers.TestMapper;
import com.eror.server.model.Role;
import com.eror.server.model.User;
import com.eror.server.service.RoleService;
import com.eror.server.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ServerApplicationTests {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    RoleMapper roleMapper;


    @Test
    void contextLoads() {

        checkUsers();
        checkROle();

    }

    private void checkUsers() {
        List<User> users = userService.findAll();

        Assertions.assertNotNull(users);
        assert users.get(0).getName().equals("admin");
    }


    private void checkROle() {
        List<Role> roleList = roleService.findAll();
        Assertions.assertNotNull(roleList);
        assert roleList.size() == 2;
        assert roleList.get(0).getId() == (1);
        assert roleList.get(1).getId() == (2);

        Role roleEntity = roleService.find((long) 1);

        RoleDTO roleDTO = roleMapper.roletoRoleDTO(roleEntity);
//
        assertEquals(roleDTO.getId(), roleEntity.getId());
//        assertEquals(roleDTO.getName(), roleEntity.getName());
    }

}
