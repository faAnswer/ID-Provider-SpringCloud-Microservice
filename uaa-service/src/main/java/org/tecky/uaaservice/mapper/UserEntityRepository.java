package org.tecky.uaaservice.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.uaaservice.entities.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    public UserEntity findByUsername(String username);


}
