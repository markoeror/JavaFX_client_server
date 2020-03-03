package com.eror.server.repository;


import com.eror.server.enums.RoleName;
import com.eror.server.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);

    Role findRoleById(Long id);
}
