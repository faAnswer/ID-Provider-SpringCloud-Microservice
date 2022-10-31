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
@Table(name = "t_user_detail")
public class UserDetailEntity {

    @Id
    private int uid;

    @Column(name = "hobby_id")
    private int hobby_id;

}