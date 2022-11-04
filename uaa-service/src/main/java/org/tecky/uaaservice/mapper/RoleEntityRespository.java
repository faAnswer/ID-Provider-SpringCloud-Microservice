package org.tecky.uaaservice.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.uaaservice.entities.RoleEntity;

public interface RoleEntityRespository extends JpaRepository<RoleEntity, String> {

    public RoleEntity findByRoleid(int roleid);

}
