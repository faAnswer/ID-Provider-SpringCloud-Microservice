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
@Table(name = "t_hobby")
public class HobbyEntity {

    @Id
    private int hobby_id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

}