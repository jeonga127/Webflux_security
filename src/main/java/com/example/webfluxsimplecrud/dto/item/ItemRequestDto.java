package com.example.webfluxsimplecrud.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemRequestDto {
    private String name;
    private int price;
    private int quantity;
}
