package org.tecky.uaaservice.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "t_oauth_scope")
public class OauthScopeEntity {

    @Id
    private int appid;

    @Column(name = "scopeid")
    private int scopeid;

}