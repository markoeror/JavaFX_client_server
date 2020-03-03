package com.eror.server.dto;


import com.eror.server.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NonNull
public class RoleDTO {
    private Long id;
    private RoleName roleName;


}
