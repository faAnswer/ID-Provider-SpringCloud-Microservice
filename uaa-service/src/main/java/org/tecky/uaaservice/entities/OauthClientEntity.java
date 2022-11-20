package org.tecky.uaaservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "t_oauth_client")
@EntityListeners(org.tecky.uaaservice.entities.listener.OauthClientEntityListener.class)
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

    @Column(name = "picuri")
    private String picuri;
}