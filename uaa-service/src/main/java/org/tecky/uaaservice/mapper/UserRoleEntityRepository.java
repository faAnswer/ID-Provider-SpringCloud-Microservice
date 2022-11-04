package org.tecky.uaaservice.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.uaaservice.entities.UserRoleEntity;

import java.util.List;

public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, String> {

    public List<UserRoleEntity> findByUid(int uid);
}
