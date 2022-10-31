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
@Table(name = "t_transaction")
public class TransactionEntity {

    @Id
    private int order_id;

    @Column(name = "uid")
    private int uid;

    @Column(name = "item_id")
    private int item_id;

    @Column(name = "qty")
    private int qty;

    @Column(name = "trans_time")
    private String trans_time;

    @Column(name = "coins_change")
    private int coins_change;

    @Column(name = "discount_id")
    private int discount_id;

}