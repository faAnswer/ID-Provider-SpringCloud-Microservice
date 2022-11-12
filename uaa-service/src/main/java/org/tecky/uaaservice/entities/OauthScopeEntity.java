package org.tecky.uaaservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "t_oauth_scope")
@IdClass(org.tecky.uaaservice.entities.comprimarykey.OauthScopePK.class)
public class OauthScopeEntity {

    @Id
    private int appid;

    @Id
    private int scopeid;
}