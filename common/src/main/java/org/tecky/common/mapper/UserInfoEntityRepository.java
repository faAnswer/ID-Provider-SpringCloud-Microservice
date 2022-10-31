package org.tecky.common.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.common.entities.UserInfoEntity;

public interface UserInfoEntityRepository extends JpaRepository<UserInfoEntity, String> {

    public UserInfoEntity findByUid(Integer uid);
}