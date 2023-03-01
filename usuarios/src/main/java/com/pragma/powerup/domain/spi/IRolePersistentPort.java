package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.RoleModel;

import java.util.Optional;

public interface IRolePersistentPort {
    void saveRole(RoleModel rolModel);
    Optional<RoleModel> getRoleByName(String name);
    Optional<RoleModel> getRoleById(Long roleId);
}
