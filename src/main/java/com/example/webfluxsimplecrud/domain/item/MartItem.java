package com.example.webfluxsimplecrud.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("martitem")
@Getter
@NoArgsConstructor
public class MartItem {
    @Id
    private Long id;

    @Column("item_id")
    private Long itemId;
    private int quantity;

    public MartItem(Long itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public void modifyItemQuantity(int quantity) {
        this.quantity = quantity;
    }
}
