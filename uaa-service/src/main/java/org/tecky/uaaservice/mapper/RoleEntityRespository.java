package org.tecky.uaaservice.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.common.entities.RoleEntity;

public interface RoleEntityRespository extends JpaRepository<RoleEntity, String> {

    public RoleEntity findByRoleid(int roleid);

}
