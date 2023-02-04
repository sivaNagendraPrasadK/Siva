package com.thrymr.hotel.management.dto;




import com.thrymr.hotel.management.entity.ItemType;


import lombok.Data;

@Data
public class MenuItemDto {
    private Long itemId;
    private String name;
    private int price;
    private ItemType itemType;


}
