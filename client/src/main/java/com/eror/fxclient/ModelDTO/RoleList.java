package com.eror.fxclient.ModelDTO;

import com.eror.fxclient.model.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleList {

    private List<Role> roles;

    public RoleList() {
        roles = new ArrayList<>();
    }
}
