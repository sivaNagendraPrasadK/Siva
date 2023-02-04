package com.thrymr.hotel.management.repository;

import com.thrymr.hotel.management.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity,String> {
    List<FileEntity> findAllByOrderByIdAsc();
}
