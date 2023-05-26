package com.example.webfluxsimplecrud.domain.item;

import com.example.webfluxsimplecrud.dto.item.ItemRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("item")
@Getter
@NoArgsConstructor
public class Item {

    @Id
    private Long id;
    private String name;
    private int price;

    public Item(ItemRequestDto itemRequestDto) {
        this.name = itemRequestDto.getName();
        this.price = itemRequestDto.getPrice();
    }
}
