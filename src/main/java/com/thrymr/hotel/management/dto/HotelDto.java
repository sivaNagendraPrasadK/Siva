package com.thrymr.hotel.management.dto;

import com.thrymr.hotel.management.entity.Address;
import com.thrymr.hotel.management.entity.FileEntity;
import com.thrymr.hotel.management.entity.MenuItem;
import lombok.Data;

import java.util.List;

@Data
public class HotelDto {
    private Long hId;
    private String name;
    private  Long contactNumber;
    private String contactEmail;
    private String aboutHotel;
    private Address address;
    private List<MenuItem> menuItemList;
    //private  String fileId;
  //  private FileDataDto fileDataDto;

    private FileDataDto  fileEntity;
}
