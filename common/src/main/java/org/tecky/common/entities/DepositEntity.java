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
@Table(name = "t_deposit")
public class DepositEntity {

    @Id
    private int deposit_id;

    @Column(name = "uid")
    private int uid;

    @Column(name = "deposit_time")
    private String deposit_time;

    @Column(name = "order_id")
    private int order_id;

}