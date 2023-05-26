package com.example.webfluxsimplecrud.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("cartitem")
@Getter
@NoArgsConstructor
public class CartItem {
    @Id
    private Long id;

    @Column("item_id")
    private Long itemId;

    @Column("cart_id")
    private Long cartId;

    private int quantity;

    public CartItem(Long itemId, Long cartId, int quantity){
        this.itemId = itemId;
        this.cartId = cartId;
        this.quantity = quantity;
    }
}
