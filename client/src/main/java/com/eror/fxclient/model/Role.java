package com.eror.fxclient.model;


import com.eror.fxclient.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
public class Role {

    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String toString() {
        return roleName.toString();

    }
}
