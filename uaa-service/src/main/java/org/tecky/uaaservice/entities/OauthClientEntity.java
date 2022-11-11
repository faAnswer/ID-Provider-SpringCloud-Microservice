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
@Table(name = "t_oauth_client")
public class OauthClientEntity {

    @Id
    private int appid;

    @Column(name = "redirecturi")
    private String redirecturi;

    @Column(name = "clientid")
    private String clientid;

    @Column(name = "clientsecret")
    private String clientsecret;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

}