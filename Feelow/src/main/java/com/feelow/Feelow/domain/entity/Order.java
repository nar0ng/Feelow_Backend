package com.feelow.Feelow.domain.entity;

import com.feelow.Feelow.domain.embedded.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Table(name = "purchase_order")
@Getter
@Entity
public class Order {

    @EmbeddedId
    private OrderNumber orderNumber;

    @Column(name = "total_amount")
    private Money totalAmount;

    @Embedded
    private Recipient recipient;

    @ManyToOne
    private Member orderer;

    @ElementCollection
    @CollectionTable (name = "order_line", joinColumns = @JoinColumn(name = "order_number"))
    @OrderColumn(name = "line_idx")
    private List<OrderLine> orderLine = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_number")
    private List<OrderStatusHistory> orderStatusHistories = new ArrayList<>();

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
