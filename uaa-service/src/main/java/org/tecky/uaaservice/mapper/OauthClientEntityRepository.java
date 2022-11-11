package org.tecky.uaaservice.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.uaaservice.entities.OauthClientEntity;

public interface OauthClientEntityRepository extends JpaRepository<OauthClientEntity, String> {

    public OauthClientEntity findByClientid(String clientid);





}