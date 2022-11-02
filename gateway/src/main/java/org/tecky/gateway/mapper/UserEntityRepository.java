package org.tecky.gateway.mapper;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.tecky.gateway.entity.UserEntity;
import reactor.core.publisher.Flux;

public interface UserEntityRepository extends ReactiveCrudRepository<UserEntity, String> {

    Flux<UserEntity> findByUsername(String username);
}
