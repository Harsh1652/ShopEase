//Cart.java
package com.example.E_Comm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private UserDetails user;

    @ManyToOne
    private Product product;

    private Integer quantity;

    @Transient
    private Double totalprice;

    @Transient
    private Double totalOrderPrice;

    public Double getTotalprice() {
        if (product != null && quantity != null) {
            return product.getDiscountPrice() * quantity;
        }
        return 0.0;
    }

    // Getter for totalOrderPrice
    public Double getTotalOrderPrice() {
        return totalOrderPrice != null ? totalOrderPrice : 0.0;
    }


}
