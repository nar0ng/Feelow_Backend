package com.feelow.Feelow.domain.embedded;

import com.feelow.Feelow.domain.entity.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class OrderLine {

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false, updatable = false)
    private Item item;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "amount", nullable = false)
    private Money amount; // 돈의 양을 의미한다

    public OrderLine(final Item item, final int quantity){
        this.item = item;
        this.quantity = quantity;
        this.amount = item.calculate(quantity);
    }

    public static OrderLine of(final Item item, final int quantity){
        return new OrderLine(item,quantity);
    }



}
