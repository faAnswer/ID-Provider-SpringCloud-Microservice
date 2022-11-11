package org.tecky.uaaservice.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tecky.uaaservice.entities.OauthScopeEntity;

import java.util.List;

public interface OauthScopeEntityRepository extends JpaRepository<OauthScopeEntity, String> {

    public List<OauthScopeEntity> findAllByAppid(Integer appid);

}