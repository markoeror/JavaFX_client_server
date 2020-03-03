package com.eror.server.mappers;

import com.eror.server.dto.RoleDTO;
import com.eror.server.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TestMapper {

    RoleDTO roleDTo(Role role);
}
