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
@Table(name = "t_item")
public class ItemEntity {

    @Id
    private int item_id;

    @Column(name = "unit_rate")
    private int unit_rate;

    @Column(name = "name")
    private String name;

}