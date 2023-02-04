package com.thrymr.hotel.management.repository;

import com.thrymr.hotel.management.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

    //List<Hotel> findAllByOrderByhIdAsc();
    List<Hotel> findAllByOrderByNameAsc();
    List<Hotel> findAllByOrderByNameDesc();
    List<Hotel> findByNameIgnoreCase(String name);

    // searching
    List<Hotel> findAllByNameIgnoreCase(String name);
    List<Hotel> findAllByContactEmailContaining(String email);
    List <Hotel> findAllBySearchKeyContainsIgnoreCase(String key);

}
