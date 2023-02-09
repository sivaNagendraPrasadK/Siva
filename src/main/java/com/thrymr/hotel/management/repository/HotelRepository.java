package com.thrymr.hotel.management.repository;

import com.thrymr.hotel.management.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

    //List<Hotel> findAllByOrderByhIdAsc();
    @Query("select h from Hotel h ORDER BY h.name Asc")
    List<Hotel> get();
    List<Hotel> findAllByOrderByNameDesc();
    List<Hotel> findByNameIgnoreCase(String name);

    // searching
    List<Hotel> findAllByNameIgnoreCase(String name);
    List<Hotel> findAllByContactEmailContaining(String email);
    List <Hotel> findAllBySearchKeyContainsIgnoreCase(String key);

    @Query("select h from Hotel h inner join h.menuItemList m where m.name= ?1")
    List<Hotel> findNameByMenuItemListName(String menuItem);

}
