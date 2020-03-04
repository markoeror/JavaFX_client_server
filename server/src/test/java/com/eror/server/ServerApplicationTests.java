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
        getUser(1);
        getUsers();
        checkROle();

    }

    private void getUser(Integer id) {
        UserDTO user = userService.findById((long) id);
        Assertions.assertNotNull(user);

    }

    private void getUsers() {
        List<UserDTO> usersDtos = userService.findAll();
        Assertions.assertNotNull(usersDtos);
        assert usersDtos.get(0).getName().equals("admin");

    }


    private void checkROle() {
        List<RoleDTO> roleListDto = roleService.findAll();
        Assertions.assertNotNull(roleListDto);
        assert roleListDto.size() == 2;
        assert roleListDto.get(0).getId() == (1);
        assert roleListDto.get(1).getId() == (2);
        RoleDTO roleDTO = roleService.find((long) 1);
        Assertions.assertNotNull(roleDTO);
        Assertions.assertNotNull(roleListDto);


    }

}
