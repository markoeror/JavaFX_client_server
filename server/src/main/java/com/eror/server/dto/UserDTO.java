package com.eror.server.dto;

import com.eror.server.model.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private RoleDTO role;


}
