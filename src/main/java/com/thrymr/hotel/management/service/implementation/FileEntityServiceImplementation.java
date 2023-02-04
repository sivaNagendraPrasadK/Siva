package com.thrymr.hotel.management.service.implementation;

import com.thrymr.hotel.management.dto.ApiResponse;
import com.thrymr.hotel.management.dto.FileDataDto;
import com.thrymr.hotel.management.entity.FileEntity;
import com.thrymr.hotel.management.exception.IdNotFoundException;
import com.thrymr.hotel.management.repository.FileEntityRepository;
import com.thrymr.hotel.management.service.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileEntityServiceImplementation implements FileService {

    @Autowired
    private FileEntityRepository fileEntityRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ApiResponse saveFile(MultipartFile fileDataDto) throws IOException {
        FileEntity fileEntity=new FileEntity();

        fileEntity.setOriginalFilename(fileDataDto.getOriginalFilename());
        fileEntity.setContentType(fileDataDto.getContentType());
        fileEntity.setBytes(fileDataDto.getBytes());
        fileEntity.setUrl("file/save/"+fileDataDto.getOriginalFilename());
        fileEntityRepository.save(fileEntity);
        return new ApiResponse(HttpStatus.OK.value(), "File saved successfully",fileEntity.getId());
    }
   /* @Override
    public FileEntity addNewFile(MultipartFile file) throws IOException {
        String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileEntity fileDB=new FileEntity(fileName,file.getContentType(),file.getBytes());
        return fileEntityRepository.save(fileDB);
    }*/

    @Override
    public List<FileDataDto> getAllFiles() {
        return fileEntityRepository.findAll().stream().map(fileEntity -> modelMapper.map(fileEntity, FileDataDto.class)).collect(Collectors.toList());
    }

   /* public List<FileDataDto> getAllFiles() {
        List<FileEntity> fileDBList=fileEntityRepository.findAllByOrderByIdAsc();
        return fileDBList.stream().map(fileDB -> {
            String fileDownloadUri= ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/file/files/")
                    .path(fileDB.getId())
                    .toUriString();
            return new FileDataDto(
                    fileDB.getId(),
                    fileDB.getOriginalFilename(),
                    fileDB.getContentType(),
                    (long) fileDB.getBytes().length,
                    fileDownloadUri);
        }).collect(Collectors.toList());
    }*/

 /*   @Override
    public ResponseEntity<byte[]> getFileById(String fileId) {
        Optional<FileEntity> fileDBOpt = fileEntityRepository.findById(fileId);
        if (fileDBOpt.isPresent()) {
            FileEntity file = fileDBOpt.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFilename() + "\"")
                    .body(file.getBytes());
        }
        return null;
    }*/


    public FileEntity getFileById(String id){
        Optional<FileEntity> optionalFileEntity = fileEntityRepository.findById(id);
        if ((optionalFileEntity.isPresent())){
            return fileEntityRepository.findById(id).get();
        }
        //return new ApiResponse(HttpStatus.OK.value(),"Success ");
        return null;
    }

    public String deleteById(String id){
        Optional<FileEntity> optionalFileEntity = fileEntityRepository.findById(id);
        if(optionalFileEntity.isPresent()){
            fileEntityRepository.deleteById(id);
            return "file deleted successfully";
        }
        else {
            throw new IdNotFoundException("Id is not found ! please enter valid Id");
        }
    }

    public String deleteAll(){
        fileEntityRepository.deleteAll();
        return "All files deleted";
    }
}
