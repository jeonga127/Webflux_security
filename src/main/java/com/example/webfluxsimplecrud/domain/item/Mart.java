package com.example.webfluxsimplecrud.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table("mart")
@NoArgsConstructor
public class Mart {
    @Id
    private Long id;
    private List<Long> martItems = new ArrayList<>();
    public void addNewItem(MartItem martItem) {
        this.martItems.add(martItem.getId());
    }
}
