package com.eror.server.service.impl;


import com.eror.server.dto.RoleDTO;
import com.eror.server.mappers.RoleMapper;
import com.eror.server.model.Role;
import com.eror.server.repository.RoleRepository;
import com.eror.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDTO save(Role entity) {
        return roleMapper.roleToRoleDTO(roleRepository.save(entity));
    }

    @Override
    public RoleDTO update(Role entity) {
        return roleMapper.roleToRoleDTO(roleRepository.save(entity));
    }

    @Override
    public void delete(Role entity) {
        roleRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<RoleDTO> deleteInBatch(List<RoleDTO> roleDTOS) {

        return null;
    }

    @Override
    public RoleDTO find(Long id) {
        return roleMapper.roleToRoleDTO(roleRepository.findRoleById(id));
    }

    @Override
    public List<RoleDTO> findAll() {

        return roleMapper.listRoleDTOs(roleRepository.findAll());
    }

    @Override
    public List<RoleDTO> findAllRolesDTO() {

        return roleMapper.listRoleDTOs(roleRepository.findAll());
    }
}
