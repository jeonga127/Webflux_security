package com.example.webfluxsimplecrud.domain.item;

import com.example.webfluxsimplecrud.dto.item.CartRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Table("cart")
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    private Long id;
    private String name;
    private List<Long> cartItems;

    public Cart(CartRequestDto cartRequestDto) {
        this.name = cartRequestDto.getName();
        this.cartItems = new ArrayList<>();
    }

    public void addToCart(CartItem cartItem) {
        this.cartItems.add(cartItem.getId());
    }

    public void removeFromCart(Item item, int quantity) {

    }
}
