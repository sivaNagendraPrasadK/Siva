package com.thrymr.hotel.management.service;

import com.thrymr.hotel.management.dto.ApiResponse;
import com.thrymr.hotel.management.dto.MenuItemDto;

import java.util.List;

public interface MenuItemService {
    MenuItemDto saveMenuItem(MenuItemDto menuItemDto);
    List<MenuItemDto> saveAllMenuItems(List<MenuItemDto> menuItemDtos);
    MenuItemDto updateMenuItem(MenuItemDto menuItemDto);
    MenuItemDto getMenuItemById(Long id);
    ApiResponse deleteMenuItem(Long id);
    List<MenuItemDto> getAllMenuItems();
    String deleteAll();

    //MenuItemDto updateMenuItemWithId(MenuItemDto menuItemDto,Long hId);


}
