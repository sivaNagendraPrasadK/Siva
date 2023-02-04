package com.thrymr.hotel.management.service;

import com.thrymr.hotel.management.dto.AddressDto;
import com.thrymr.hotel.management.dto.ApiResponse;
import com.thrymr.hotel.management.dto.HotelDto;
import com.thrymr.hotel.management.dto.MenuItemDto;
import com.thrymr.hotel.management.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HotelService {
    HotelDto saveHotel(HotelDto hotelDto);
    List<HotelDto> saveAll(List<HotelDto> hotelDtos);
    HotelDto updateHotel(HotelDto hotelDto);
    String deleteHotel(Long hId) throws IdNotFoundException;
    //ApiResponse getHotelById(Long hId) throws IdNotFoundException;
    HotelDto getHotelById(Long hId);
    List<HotelDto> getAllHotels();
    String deleteAll();


    String addNewMenuItem(MenuItemDto menuItemDto, Long hId); // working
    List<MenuItemDto> addNewMenuItems(List<MenuItemDto> menuItemDto, Long hId); // adding more items
    String updateAddress(AddressDto addressDto,Long hId); // working


    List<HotelDto> findAllByNameOrder(String name); // working

    List<MenuItemDto> getAllMenuItemByName(String name); // working

    List<MenuItemDto> getAllMenuItemData(Long hId); // working

    List<HotelDto> sortAllFieldsByAsc(String field); // working
    List<HotelDto> sortAllFieldsByDesc(String field); // working

    List<HotelDto> sortAllHotelFieldsByOrder(String field, String order);
    List<MenuItemDto> sortAllMenuItemFieldsByAsc(String field); // working
    List<MenuItemDto> sortMenuItemAvailInHotelByAscOrder(Long hId, String field); // partially working
    List<HotelDto> getHotelField(); // working get only hotel fields

    //pagination
    Page<HotelDto> findHotelWithPagination(int offset, int pageSize);
    // pagination Asc order for any field
    Page<HotelDto> findHotelWithPaginationAndSortingFieldByAsc(int offset, int pageSize, String filed); // it is working for hotel fields and address fields Pending with menuItem fields
    // pagination Desc order for any field
    Page<HotelDto> findHotelWithPaginationAndSortingFieldByDesc(int offset, int pageSize, String filed);

    Page<MenuItemDto> findMenuItemWithPaginationAndSortingFieldByAsc(int offset,int pageSize, String field);

    MenuItemDto getMenuItemById(Long hId, Long itemId); // working ... getting item by Hotel id and item id

    String deleteMenuItemById(Long hId, Long itemId);

    Long countOfHotels();

    //MenuItemDto countAllMenuItems(Long hId)

    List<HotelDto> findHotelByName(String name);
    List<HotelDto> findHotelByContactEmail(String email);

    List<HotelDto> searchByHotelData(String key);

    ApiResponse UpdateMenuItem(MenuItemDto menuItemDto, Long hId);

}
