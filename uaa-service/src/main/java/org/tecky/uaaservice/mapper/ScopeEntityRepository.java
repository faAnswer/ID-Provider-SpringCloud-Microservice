package org.tecky.uaaservice.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.uaaservice.entities.ScopeEntity;

public interface ScopeEntityRepository extends JpaRepository<ScopeEntity, String> {

    public ScopeEntity findByScopeid(Integer scopeid);

}