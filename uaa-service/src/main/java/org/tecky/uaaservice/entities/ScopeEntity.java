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
@Table(name = "t_scope")
public class ScopeEntity {

    @Id
    private int scopeid;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}