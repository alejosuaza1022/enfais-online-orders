package com.enfasis.onlineorders.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "orders_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Order order;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(((OrderProduct) o).getOrder().getId(), that.getOrder().getId()) &&
                Objects.equals(((OrderProduct) o).getProduct().getId(), that.getProduct().getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
