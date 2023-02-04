package com.thrymr.hotel.management.service.implementation;

import com.thrymr.hotel.management.dto.*;

import com.thrymr.hotel.management.entity.Address;
import com.thrymr.hotel.management.entity.FileEntity;
import com.thrymr.hotel.management.entity.Hotel;
import com.thrymr.hotel.management.entity.MenuItem;
import com.thrymr.hotel.management.exception.IdNotFoundException;
import com.thrymr.hotel.management.repository.AddressRepository;
import com.thrymr.hotel.management.repository.FileEntityRepository;
import com.thrymr.hotel.management.repository.HotelRepository;
import com.thrymr.hotel.management.repository.MenuItemRepository;
import com.thrymr.hotel.management.service.HotelService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImplementation implements HotelService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private FileEntityRepository fileEntityRepository;

    @Override
    public HotelDto saveHotel(HotelDto hotelDto)  {
        Hotel hotel=modelMapper.map(hotelDto, Hotel.class);
        hotel.setSearchKey(getSearchKeyData(hotel));
        //hotel.setFileId(hotelDto.getFileId());
        Optional<FileEntity> fileEntity=fileEntityRepository.findById(hotelDto.getFileEntity().getId());
        if(fileEntity.isPresent()) {
            hotel.setFileEntity(fileEntity.get());
        }
       Hotel hotel1 = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }


    @Override
    public List<HotelDto> saveAll(List<HotelDto> hotelDtos) {
        List<Hotel> hotels = modelMapper.map(hotelDtos, new TypeToken<List<Hotel>>(){}.getType());
        hotelRepository.saveAll(hotels);
        return hotelRepository.findAll().stream().map(hotel -> modelMapper.map(hotel,HotelDto.class)).collect(Collectors.toList());
    }

    @Override
    public HotelDto updateHotel(HotelDto hotelDto) {
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setSearchKey(getSearchKeyData(hotel));
        return modelMapper.map(hotelRepository.save(hotel),HotelDto.class);
    }

    @Override
    public String  deleteHotel(Long hId)  {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hId);
        if (hotelOptional.isPresent()) {
            hotelRepository.deleteById(hId);
            return "Deleted successfully";
        } else {
            throw new IdNotFoundException("Id is not found ! please enter valid Id");
        }
    }

  /*  @Override
    public ApiResponse getHotelById(Long hId){
        try {
            Hotel hotel = hotelRepository.findById(hId).get();
            FileDataDto dto=new FileDataDto();
            if(hotel.getFileId()!=null){
            Optional<FileEntity>optionalFileEntity=fileEntityRepository.findById(hotel.getFileId());

            if(optionalFileEntity.isPresent()){
                dto.setId(optionalFileEntity.get().getId());
                dto.setOriginalFilename(optionalFileEntity.get().getOriginalFilename());
                dto.setBytes(optionalFileEntity.get().getBytes());
                dto.setContentType(optionalFileEntity.get().getContentType());
            }
            }
            return  new ApiResponse(HttpStatus.OK.value(),modelMapper.map(hotel, HotelDto.class),dto );
        } catch (Exception exception) {
            throw new IdNotFoundException("Id is not found ! please enter valid Id");
        }
    }*/
   @Override
   public HotelDto getHotelById(Long hId){
       try {
           Hotel hotel = hotelRepository.findById(hId).get();
           return modelMapper.map(hotel, HotelDto.class);
       } catch (Exception exception) {
           throw new IdNotFoundException("Id is not found ! please enter valid Id");
       }
       //return modelMapper.map(hotelRepository.findById(hId).orElseThrow(), HotelDto.class);
   }


    @Override
    public List<HotelDto> getAllHotels() {
       List<Hotel> hotelList = hotelRepository.findAll(Sort.by("hId").ascending());
        return hotelRepository.findAll().stream().map(hotel -> modelMapper.map(hotel,HotelDto.class)).collect(Collectors.toList());
    }

    @Override
    public String deleteAll() {
        hotelRepository.deleteAll();
        return "All Hotel are deleted";
    }



    public String addNewMenuItem(MenuItemDto menuItemDto, Long hId){
        Optional<Hotel> hotel= hotelRepository.findById(hId);
        if(hotel.isPresent()){
            Hotel hotel1 = hotel.get();
            MenuItem menuItem = new MenuItem();
            menuItem.setItemId(menuItemDto.getItemId());
            menuItem.setName(menuItemDto.getName());
            menuItem.setItemType(menuItemDto.getItemType());
            menuItem.setPrice(menuItemDto.getPrice());
            hotel1.getMenuItemList().add(menuItem);
            hotel1.setSearchKey(hotel1.getSearchKey());
            hotelRepository.save(hotel1);
            return "New item saved successfully";
        }
        throw new IdNotFoundException("Id is not found ! please enter valid Id");
    }

    public List<MenuItemDto> addNewMenuItems(List<MenuItemDto> menuItemDtos1, Long hId) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hId);
        List<MenuItem> menuItemDtos = new ArrayList<>();
        if (hotelOptional.isPresent()) {
            Hotel hotel1 = hotelOptional.get();
            for (MenuItemDto menuItemDto1 : menuItemDtos1) {
                MenuItem menuItem = new MenuItem();
                menuItem.setItemId(menuItemDto1.getItemId());
                menuItem.setName(menuItemDto1.getName());
                menuItem.setItemType(menuItemDto1.getItemType());
                menuItem.setPrice(menuItemDto1.getPrice());
                menuItemDtos.add(menuItem);
            }
            menuItemDtos = menuItemRepository.saveAll(menuItemDtos);
            hotel1.getMenuItemList().addAll(menuItemDtos);
            hotel1.setSearchKey(hotel1.getSearchKey());
            hotelRepository.save(hotel1);
            return menuItemDtos.stream().map(menuItem -> modelMapper.map(menuItem,MenuItemDto.class)).toList();
        }
        else {
            throw new IdNotFoundException("Id is not found ! please enter valid Id");
        }
    }


    public String updateAddress(AddressDto addressDto, Long hId) {
        Optional<Hotel> hotelOptional=hotelRepository.findById(hId);
        if(hotelOptional.isPresent()){
            Hotel hotel=hotelOptional.get();
            Address address=new Address();
            address.setAId(addressDto.getAId());
            address.setFloorNo(addressDto.getFloorNo());
            address.setLandMark(addressDto.getLandMark());
            address.setFullAddress(addressDto.getFullAddress());
            hotel.setAddress(address);
            hotelRepository.save(hotel);
            return "updated the address successfully";
        }else {
            throw new IdNotFoundException("Id is not found ! please enter valid Id");
        }
    }


    public ApiResponse UpdateMenuItem(MenuItemDto menuItemDto, Long hId){
        Optional<Hotel> hotelOptional = hotelRepository.findById(hId);
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            Optional<MenuItem> optionalMenuItem = hotel.getMenuItemList().stream().filter(menuItem -> menuItem.getItemId().equals(menuItemDto.getItemId())).findFirst();
            MenuItem updateMenuItem=new MenuItem();
            if (optionalMenuItem.isPresent()) {
               updateMenuItem = optionalMenuItem.get();
                // menuItem.setItemId(menuItemDto.getItemId());
                updateMenuItem.setName(menuItemDto.getName());
                updateMenuItem.setPrice(menuItemDto.getPrice());
                updateMenuItem.setItemType(menuItemDto.getItemType());
                hotel.getMenuItemList().add(updateMenuItem);
            }
            hotelRepository.save(hotel);
            return new ApiResponse(HttpStatus.OK.value(),"Menu item updated successfully",updateMenuItem);
        } else {
            throw new IdNotFoundException("Id is not found ! please enter valid Id");
        }
    }
   /* public String UpdateMenuItem(MenuItemDto menuItemDto,Long hId){
        if(menuItemRepository.findById(hId).isPresent()){
            MenuItem menuItem= menuItemRepository.findById(hId).get();
            menuItem.setItemId(menuItemDto.getItemId());
            menuItem.setName(menuItemDto.getName());
            menuItem.setPrice(menuItemDto.getPrice());
            menuItem.setItemType(menuItemDto.getItemType());
            MenuItem updateMenu = menuItemRepository.save(menuItem);
            return "Menu item updated successfully";
        }
        //throw new IdNotFoundException("Id is not found ! please enter valid Id");
        return "Cont update";
    }
*/
    public List<MenuItemDto> getAllMenuItemByName(String name){
        List<MenuItemDto> menuItemDtos;
        menuItemDtos= hotelRepository.findByNameIgnoreCase(name).get(0).getMenuItemList().stream()
                .map(menuItem -> modelMapper.map(menuItem,MenuItemDto.class)).toList();
        return menuItemDtos;
    }



    public List<HotelDto> findAllByNameOrder(String name){     // this is for only name
        List<Hotel> hotels = new ArrayList<>();
        if(name.equalsIgnoreCase("Asc")){
            hotels = hotelRepository.findAllByOrderByNameAsc();
        } else if (name.equalsIgnoreCase("Desc")) {
            hotels=hotelRepository.findAllByOrderByNameDesc();
        }
        return hotels.stream().map(hotel -> modelMapper.map(hotel,HotelDto.class)).collect(Collectors.toList());
    }

    public List<HotelDto> sortAllFieldsByAsc(String field){   // it sorts all fields in ASc Order
        return hotelRepository.findAll(Sort.by(Sort.Direction.ASC, field))
                .stream().map(hotel -> modelMapper.map(hotel, HotelDto.class)).toList();

    }
    @Override
    public List<HotelDto> sortAllFieldsByDesc(String field) {     // it sorts all fields in Desc order
        return hotelRepository.findAll(Sort.by(Sort.Direction.DESC, field)).stream().map(hotel -> modelMapper.map(hotel, HotelDto.class)).toList();
    }
    @Override
    public List<MenuItemDto> sortAllMenuItemFieldsByAsc(String field) {     //
        return menuItemRepository.findAll(Sort.by(Sort.Direction.ASC, field)).stream().map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class)).collect(Collectors.toList());
    }

    public List<HotelDto> sortAllHotelFieldsByOrder(String field, String order){
        if(order.equalsIgnoreCase("ASC")){
            return hotelRepository.findAll(Sort.by(Sort.Direction.DESC, field)).stream().map(hotel -> modelMapper.map(hotel, HotelDto.class)).toList();
        } else if (order.equalsIgnoreCase("DESC")) {
            return hotelRepository.findAll(Sort.by(Sort.Direction.DESC, field)).stream().map(hotel -> modelMapper.map(hotel, HotelDto.class)).toList();
        }
        else {
            return null;
        }
    }

    public List<MenuItemDto> getAllMenuItemData(Long hId){
        List<MenuItemDto> menuItemDtos = null;
        try {
            menuItemDtos=hotelRepository.findById(hId).get().getMenuItemList().stream().map(menuItem -> modelMapper.map(menuItem,MenuItemDto.class)).toList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return menuItemDtos;
    }




    @Override
    public List<MenuItemDto> sortMenuItemAvailInHotelByAscOrder(Long hId, String field) {
        List<MenuItemDto> menuItemDtos=null;
        if(hotelRepository.findById(hId).isPresent()){
            menuItemDtos=menuItemRepository.findAll(Sort.by(Sort.Direction.ASC, field)).stream().map(menuItem -> modelMapper.map(menuItem,MenuItemDto.class)).collect(Collectors.toList());
        }
        return menuItemDtos;
    }

    @Override
    public List<HotelDto> getHotelField() {
        List<HotelDto> hotelDtoList=new ArrayList<>();
        List<Hotel> hotelList=hotelRepository.findAll();
        for(Hotel hotel:hotelList){
            HotelDto hotelDto1=new HotelDto();
            hotelDto1.setHId(hotel.getHId());
            hotelDto1.setName(hotel.getName());
            hotelDto1.setContactNumber(hotel.getContactNumber());
            hotelDto1.setContactEmail(hotel.getContactEmail());
            hotelDto1.setAboutHotel(hotel.getAboutHotel());
            hotelDtoList.add(hotelDto1);
        }
        return hotelDtoList;
    }

    public Page<HotelDto> findHotelWithPagination(int offset, int pageSize){
        Page<Hotel> hotelPage= hotelRepository.findAll(PageRequest.of(offset, pageSize));
        return hotelPage.map(hotel -> modelMapper.map(hotel,HotelDto.class));
    }

    // pagination Asc order for any field
    public Page<HotelDto> findHotelWithPaginationAndSortingFieldByAsc(int offset, int pageSize, String field){
        Page<Hotel> hotelPage= hotelRepository.findAll(PageRequest.of(offset,pageSize,Sort.Direction.ASC,field));
        return hotelPage.map(hotel -> modelMapper.map(hotel,HotelDto.class));
    }

    // pagination Desc order for any field
    public Page<HotelDto> findHotelWithPaginationAndSortingFieldByDesc(int offset, int pageSize, String field){
        Page<Hotel> hotelPage= hotelRepository.findAll(PageRequest.of(offset,pageSize,Sort.Direction.DESC,field));
        return hotelPage.map(hotel -> modelMapper.map(hotel,HotelDto.class));
    }

    public Page<MenuItemDto> findMenuItemWithPaginationAndSortingFieldByAsc(int offset, int pageSize, String field){
        Page<MenuItem> menuItemPage= menuItemRepository.findAll(PageRequest.of(offset,pageSize,Sort.Direction.ASC,field));
        return menuItemPage.map(menuItem -> modelMapper.map(menuItem,MenuItemDto.class));
    }

    // getting menuItem By hotel and item id
    public MenuItemDto getMenuItemById(Long hid, Long itemId) {
        MenuItemDto menuItemDto = null;
        Optional<Hotel> hotelOptional = hotelRepository.findById(hid);
        if(hotelOptional.isPresent()) {
            Hotel hotel = hotelRepository.findById(hid).get(); // working
            Optional<MenuItem> menuItem = menuItemRepository.findById(itemId); // working
            menuItemDto = modelMapper.map(menuItem, MenuItemDto.class);
            return menuItemDto;
        }
        else {
            throw new IdNotFoundException("Id is not found ! please enter valid Id");
        }
    }

    // delete menuItem by hotel id and item id
    public String deleteMenuItemById(Long hId, Long itemId){
        Optional<Hotel> hotelOptional = hotelRepository.findById(hId);
            if (hotelOptional.isPresent()) {
                menuItemRepository.deleteById(itemId);
                return "Deleted record successfully";
            } else {
                throw new IdNotFoundException("Id is not found ! please enter valid Id");
            }
    }


    @Override
    public Long countOfHotels(){
        return hotelRepository.count();
    }
    @Override
    public List<HotelDto> findHotelByName(String name) {
        List<Hotel> hotelList = hotelRepository.findAllByNameIgnoreCase(name);
        return hotelList.stream().map(hotel -> modelMapper.map(hotel,HotelDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<HotelDto> findHotelByContactEmail(String email) {
        List<Hotel> hotelList = hotelRepository.findAllByContactEmailContaining(email);
        return hotelList.stream().map(hotel -> modelMapper.map(hotel,HotelDto.class)).collect(Collectors.toList());
    }


    private String getSearchKeyData(Hotel hotel){
        String searchKey= "";
        if(hotel.getName()!=null){
            searchKey = searchKey+hotel.getName();
        }if(hotel.getContactNumber() != null){
            searchKey = searchKey+hotel.getContactNumber();
        }if(hotel.getContactEmail() != null){
            searchKey = searchKey+hotel.getContactEmail();
        }if(hotel.getAboutHotel() != null){
            searchKey = searchKey+hotel.getAboutHotel();
        }if(hotel.getAddress().getFloorNo() != null) {
            searchKey = searchKey +hotel.getAddress().getFloorNo();
        }if (hotel.getAddress().getLandMark() != null){
            searchKey = searchKey+hotel.getAddress().getLandMark();
        }if (hotel.getAddress().getFullAddress() != null){
            searchKey = searchKey+hotel.getAddress().getFullAddress();
        }if(hotel.getMenuItemList() != null){
            searchKey = searchKey+hotel.getMenuItemList();
        }
        return searchKey;
    }

    @Override
    public List<HotelDto> searchByHotelData(String key) {
        List<Hotel> hotels= hotelRepository.findAllBySearchKeyContainsIgnoreCase(key);
        return hotels.stream().map(hotel -> (modelMapper.map(hotel,HotelDto.class))).collect(Collectors.toList());
    }



}




