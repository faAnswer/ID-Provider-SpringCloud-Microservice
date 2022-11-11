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
@Table(name = "t_user_authorize")
public class UserAuthorizeEntity {

    @Id
    private int uid;

    @Column(name = "appid")
    private int appid;

}