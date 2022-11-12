package org.tecky.uaaservice.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.uaaservice.entities.OauthClientEntity;
import org.tecky.uaaservice.entities.OauthScopeEntity;

import java.util.List;

public interface OauthClientEntityRepository extends JpaRepository<OauthClientEntity, String> {

    public OauthClientEntity findByClientid(String clientid);

    public List<OauthClientEntity> findAllByStatus(String status);


}