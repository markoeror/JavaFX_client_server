package com.eror.server.service;


import com.eror.server.dto.RoleDTO;
import com.eror.server.generic.GenericService;
import com.eror.server.model.Role;

import java.util.List;

public interface RoleService extends GenericService<Role, RoleDTO> {

    List<RoleDTO> findAllRolesDTO();
}
