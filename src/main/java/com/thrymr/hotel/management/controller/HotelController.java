package com.thrymr.hotel.management.controller;

import com.thrymr.hotel.management.dto.AddressDto;
import com.thrymr.hotel.management.dto.ApiResponse;
import com.thrymr.hotel.management.dto.HotelDto;
import com.thrymr.hotel.management.dto.MenuItemDto;
import com.thrymr.hotel.management.exception.IdNotFoundException;
import com.thrymr.hotel.management.service.HotelService;
import com.thrymr.hotel.management.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    private MenuItemService menuItemService;
    @PostMapping(path = "/save")
    public HotelDto  saveHotel(@RequestBody HotelDto hotelDto){
        return hotelService.saveHotel(hotelDto);
    }
    @PostMapping(path = "/saveAll")
    public List<HotelDto> saveAll(@RequestBody List<HotelDto> hotelDtos){
        return hotelService.saveAll(hotelDtos);
    }
    @PutMapping(path = "/update")
    public HotelDto updateHotel(@RequestBody HotelDto hotelDto){
        return hotelService.updateHotel(hotelDto);
    }
    @DeleteMapping(path = "/deleteById/{hId}") // /api/hotel/deleteById/1
    public ResponseEntity <String>  deleteHotel(@PathVariable ("hId")Long id)throws IdNotFoundException {
        return new ResponseEntity<>(hotelService.deleteHotel(id), HttpStatus.OK);
    }
   /* @GetMapping(path = "/getById/{hId}")
    public ApiResponse getHotelById(@PathVariable Long hId){
        return hotelService.getHotelById(hId);
    }*/
    @GetMapping(path = "/getById/{hId}")
    public HotelDto getHotelById(@PathVariable Long hId){
        return hotelService.getHotelById(hId);
    }
    @GetMapping(path = "/getAll")
    public List<HotelDto> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @DeleteMapping(path = "/deleteAll")
    public String deleteAll(){
        return hotelService.deleteAll();
    }

    // adding item to existing hotel
    @PostMapping(path = "/save/item/{hId}")
    public String addNewMenuItem(@RequestBody MenuItemDto menuItemDto, @PathVariable("hId")Long hId){
        return hotelService.addNewMenuItem(menuItemDto,hId);
    }

    @PostMapping(path = "/addListOfItems/{hId}")
    public List<MenuItemDto> addNewMenuItems(@RequestBody List<MenuItemDto> menuItemDto, @PathVariable Long hId){
        return hotelService.addNewMenuItems(menuItemDto, hId);
    }
    // to update address of existing hotel
    @PutMapping(path = "/update/address/{hId}")
    public String updateAddress(@RequestBody AddressDto addressDto,@PathVariable ("hId")Long hId){
        return hotelService.updateAddress(addressDto, hId);
    }

    // get hotel by Asc or Desc order
    @GetMapping(path = "/getByName/{order}") // (name) Asc or desc
    public List<HotelDto> findAllByNameOrder(@PathVariable("order") String name){
        return hotelService.findAllByNameOrder(name);
    }

    @GetMapping(path = "/hotelSorting/byAsc/{field}")
    public List<HotelDto> sortAllFieldsByAsc(@PathVariable ("field")String field){
        return hotelService.sortAllFieldsByAsc(field);
    }
    @GetMapping("/hotel/sortingBy/desc/{field}")
    public List<HotelDto> sortAllFieldsByDesc(@PathVariable("field") String field){
        return hotelService.sortAllFieldsByDesc(field);
    }

    @GetMapping(path = "/getAllItemsBy/hotelName/{name}")
    public List<MenuItemDto> getAllMenuItemByName(@PathVariable String name){
        return hotelService.getAllMenuItemByName(name);
    }


    // getting all menuItem by hotel id
    @GetMapping(path = "/itemBy/id/{id}")
    public List<MenuItemDto> getAllMenuItemData(@PathVariable ("id") Long id){
        return hotelService.getAllMenuItemData(id);
    }


    @GetMapping("/sort/menuItemBy/asRequestBodyc/{field}")
    public List<MenuItemDto> sortAllMenuItemFieldsByAsc(@PathVariable String field){
        return hotelService.sortAllMenuItemFieldsByAsc(field);
    }
    @GetMapping("/sort/menuItem/by/asc/{id}/{field}")
    public List<MenuItemDto> sortMenuItemAvailInHotelByAscOrder(@PathVariable Long id,@PathVariable String field){
        return hotelService.sortMenuItemAvailInHotelByAscOrder(id,field);
    }
    @GetMapping(path = "/getOnly/hotelFields")
    public List<HotelDto> geHotelField(){
        return hotelService.getHotelField();
    }

    @GetMapping(path = "/pagination/{offset}/{pageSize}")
    public Page<HotelDto> findHotelWithPagination(@PathVariable int offset, @PathVariable int pageSize){
        return hotelService.findHotelWithPagination(offset, pageSize);
    }

    @GetMapping(path = "/paginationWith/sortAsc/{offset}/{pageSize}/{field}")
    public Page<HotelDto> findHotelWithPaginationAndSortingFieldByAsc(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return hotelService.findHotelWithPaginationAndSortingFieldByAsc(offset, pageSize, field);
    }

    @GetMapping(path = "/paginationWith/sortDesc/{offset}/{pageSize}/{field}")
    public Page<HotelDto> findHotelWithPaginationAndSortingFieldByDesc(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return hotelService.findHotelWithPaginationAndSortingFieldByDesc(offset, pageSize, field);
    }

    @GetMapping(path = "/menuItemPagination/sortAsc/{offset}/{pageSize}/{field}")
    public Page<MenuItemDto> findMenuItemWithPaginationAndSortingFieldByAsc(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return hotelService.findMenuItemWithPaginationAndSortingFieldByAsc(offset, pageSize, field);
    }
    @GetMapping(path = "/getMenuItem/withHid-itemId/{hId}/{itemId}")
    public MenuItemDto getMenuItemById(@PathVariable Long hId, @PathVariable Long itemId){
        return hotelService.getMenuItemById(hId, itemId);
    }

    @DeleteMapping(path = "/deleteMenuItem/withHid-itemIt/{hId}/{itemId}")
    public String deleteMenuItemById(@PathVariable Long hId, @PathVariable Long itemId){
        return hotelService.deleteMenuItemById(hId, itemId);
    }


    @GetMapping(path = "/countOfHotel")
    public Long countOfHotels(){
        return hotelService.countOfHotels();
    }


    @GetMapping(path = "/searching/{key}")
    public List<HotelDto> searchByHotelData(@PathVariable ("key")String key){
        return hotelService.searchByHotelData(key);
    }

    @GetMapping(path = "/getByHotelName/{name}")
    public List<HotelDto> findHotelByName(@PathVariable String name){
        return hotelService.findHotelByName(name);
    }
    @GetMapping(path = "/getByHotelEmail/{email}")
    public List<HotelDto> findHotelByContactEmail(@PathVariable String email){
        return hotelService.findHotelByContactEmail(email);
    }

    @PutMapping(path = "/updateMenuItem/{hId}")
    public ApiResponse UpdateMenuItem(@RequestBody MenuItemDto menuItemDto, @PathVariable Long hId){
        return hotelService.UpdateMenuItem(menuItemDto, hId);
    }
   /* @PutMapping(path = "/upItem/{hId}")
    public MenuItemDto updateMenuItemWithId(@RequestBody MenuItemDto menuItemDto,Long hId){
        return menuItemService.updateMenuItemWithId(menuItemDto, hId);
    }*/
}
