package com.thrymr.hotel.management.service;

import com.thrymr.hotel.management.dto.ApiResponse;
import com.thrymr.hotel.management.dto.FileDataDto;
import com.thrymr.hotel.management.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    ApiResponse saveFile (MultipartFile fileDataDto) throws IOException;

    //FileEntity addNewFile(MultipartFile file) throws IOException;
    List<FileDataDto> getAllFiles();
    //List<FileDataDto> getAllFiles();

    FileEntity getFileById(String id);
    //ResponseEntity<byte[]> getFileById(String fileId);

    String deleteById(String id);

    String deleteAll();
}
