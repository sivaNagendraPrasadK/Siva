package com.thrymr.hotel.management.service.implementation;

import com.thrymr.hotel.management.dto.ApiResponse;
import com.thrymr.hotel.management.dto.MenuItemDto;
import com.thrymr.hotel.management.entity.Hotel;
import com.thrymr.hotel.management.entity.MenuItem;
import com.thrymr.hotel.management.exception.IdNotFoundException;
import com.thrymr.hotel.management.repository.HotelRepository;
import com.thrymr.hotel.management.repository.MenuItemRepository;
import com.thrymr.hotel.management.service.MenuItemService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImplementation implements MenuItemService {
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public MenuItemDto saveMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setItemType(menuItemDto.getItemType());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItemRepository.save(menuItem);
        menuItemDto.setItemId(menuItem.getItemId());
        menuItemDto.setName(menuItem.getName());
        menuItemDto.setItemType(menuItem.getItemType());
        menuItemDto.setPrice(menuItem.getPrice());
        /*MenuItem menuItem=modelMapper.map(menuItemDto,MenuItem.class);
        return modelMapper.map(menuItemRepository.save(menuItem),MenuItemDto.class);*/

        return menuItemDto;
    }

    @Override
    public List<MenuItemDto> saveAllMenuItems(List<MenuItemDto> menuItemDtos) {
        List<MenuItem> menuItemList=modelMapper.map(menuItemDtos,new TypeToken<List<MenuItem>>(){}.getType());
        return menuItemList.stream().map(project -> modelMapper.map(project,MenuItemDto.class)).collect(Collectors.toList());
    }

    @Override
    public MenuItemDto updateMenuItem(MenuItemDto menuItemDto) {

        MenuItem menuItem=modelMapper.map(menuItemDto,MenuItem.class);
        return modelMapper.map(menuItemRepository.save(menuItem),MenuItemDto.class);
    }

    @Override
    public ApiResponse deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
        return new ApiResponse(HttpStatus.OK.value(),"Item deleted successfully");
    }

    @Override
    public MenuItemDto getMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).get();
        return modelMapper.map(menuItem, MenuItemDto.class);
    }

    @Override
    public List<MenuItemDto> getAllMenuItems() {
        return menuItemRepository.findAll().stream().map(menuItem -> modelMapper.map(menuItem,MenuItemDto.class)).collect(Collectors.toList());

    }

    @Override
    public String deleteAll() {
        menuItemRepository.deleteAll();
        return "All items are deleted";
    }


    /*public MenuItemDto updateMenuItemWithId(MenuItemDto menuItemDto, Long hId){
        try {
            Optional<Hotel> hotelOptional= hotelRepository.findById(hId);
            MenuItem menuItemD =modelMapper.map(menuItemDto,MenuItem.class);
            menuItemD = menuItemRepository.save(menuItemD);
            return modelMapper.map(menuItemD,MenuItemDto.class);
        }
        catch (Exception e){
            throw new IdNotFoundException("no id");
        }
    }*/

}
