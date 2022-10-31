package org.tecky.common.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.common.entities.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    public UserEntity findByEmail(String email);
}