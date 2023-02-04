package com.thrymr.hotel.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long itemId;
    private String name;
    private int price;
    @Enumerated(EnumType.STRING)
    private ItemType itemType;






}
