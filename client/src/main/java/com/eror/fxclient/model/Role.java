package com.eror.fxclient.model;

import com.eror.fxclient.enums.RoleNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleNames name;


    @Override
    public String toString() {
        return name.toString();
    }
}
