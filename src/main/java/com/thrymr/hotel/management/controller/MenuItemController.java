package com.thrymr.hotel.management.controller;

import com.thrymr.hotel.management.dto.ApiResponse;
import com.thrymr.hotel.management.dto.MenuItemDto;
import com.thrymr.hotel.management.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping(path = "/saveMenuItem")
    public MenuItemDto saveMenuItem(@RequestBody MenuItemDto menuItemDto){
        return menuItemService.saveMenuItem(menuItemDto);
    }

    @PostMapping(path = "/saveAll/MenuItems")
    public List<MenuItemDto> saveAllMenuItems(@RequestBody List<MenuItemDto> menuItemDto){
        return menuItemService.saveAllMenuItems(menuItemDto);
    }
    @PutMapping(path = "/updateItem")
    public MenuItemDto updateMenuItem(@RequestBody MenuItemDto menuItemDto){
        return menuItemService.updateMenuItem(menuItemDto);
    }
    @GetMapping(path = "/getItem/byId/{id}")
    public MenuItemDto getMenuItemById(@PathVariable Long id){
        return menuItemService.getMenuItemById(id);
    }
    @DeleteMapping(path = "/deleteItem/byId/{id}")
    public ApiResponse deleteMenuItem(@PathVariable Long id){
        return menuItemService.deleteMenuItem(id);
    }
    @GetMapping(path = "/getAll/Items")
    public List<MenuItemDto> getAllMenuItems(){
        return menuItemService.getAllMenuItems();
    }
}
