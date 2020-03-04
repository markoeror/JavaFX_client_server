package com.eror.server.mappers;

import com.eror.server.dto.RoleDTO;
import com.eror.server.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "role.name", target = "roleName")
    RoleDTO roleToRoleDTO(Role role);


    List<RoleDTO> listRoleDTOs(List<Role> setRoles);

}
