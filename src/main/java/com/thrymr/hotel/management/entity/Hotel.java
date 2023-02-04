package com.thrymr.hotel.management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long hId;
    private String name;
    private  Long contactNumber;
    private String contactEmail;
    @Column(columnDefinition = "TEXT")
    private String aboutHotel;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(fetch = FetchType.EAGER,targetEntity = MenuItem.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_fk",referencedColumnName = "hId")
    private List<MenuItem> menuItemList = new ArrayList<>();
    @Column(columnDefinition = "TEXT")
    private String searchKey;
   /* @Column(name = "fil_id")
    private String fileId;*/
    @OneToOne(fetch = FetchType.EAGER)
    private FileEntity fileEntity;
}
