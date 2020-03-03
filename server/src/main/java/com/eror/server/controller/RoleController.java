package com.eror.server.controller;

import com.eror.server.dto.RoleDTO;
import com.eror.server.model.Role;
import com.eror.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping(value = "getRoles")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<RoleDTO>> getRoles() {
        List<RoleDTO> listRoleDtos = roleService.findAllRolesDTO();
        return new ResponseEntity<>(listRoleDtos, HttpStatus.OK);
    }
}
