package org.tecky.userresource.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.userresource.entities.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    public UserEntity findByUsername(String username);

}