package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.spi.IRolePersistentPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RolePersistenceAdapter implements IRolePersistentPort {
    private final IRoleRepository rolRepository;
    private final IRoleEntityMapper roleEntityMapper;
    @Override
    public void saveRole(RoleModel roleModel) {
        this.rolRepository.save(this.roleEntityMapper.toEntity(roleModel));
    }

    /**
     * Search for role by its name
     *
     * @param name - role id
     * @return optional role model or null
     * */
    @Override
    public Optional<RoleModel> getRoleByName(String name) {
        RoleEntity roleEntity = this.rolRepository.findByName(name).orElse(null);
        return Optional.ofNullable(this.roleEntityMapper.toRolModel(roleEntity));
    }

    /**
     * Search for role by its id
     *
     * @param roleId - role id
     * @return optional role model or null
     * */
    @Override
    public Optional<RoleModel> getRoleById(Long roleId) {
        RoleEntity roleEntity = this.rolRepository.findById(roleId).orElse(null);
        return Optional.ofNullable(this.roleEntityMapper.toRolModel(roleEntity));
    }
}
