package org.tecky.common.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "t_user")
public class UserEntity {

    @Id
    private int uid;

    @Column(name = "roles")
    private String roles;

    @Column(name = "username")
    private String username;

    @Column(name = "shapassword")
    private String shapassword;

    @Column(name = "salt")
    private String salt;

    @Column(name = "email")
    private String email;
}