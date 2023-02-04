package com.thrymr.hotel.management.controller;

import com.thrymr.hotel.management.dto.ApiResponse;
import com.thrymr.hotel.management.dto.FileDataDto;
import com.thrymr.hotel.management.entity.FileEntity;
import com.thrymr.hotel.management.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileEntityController {

    @Autowired
    FileService fileService;

    @PostMapping(path = "/save")
    public ApiResponse saveFile(@RequestParam(value = "file") MultipartFile file)  throws IOException {
        return fileService.saveFile(file);
    }

    /*@PostMapping("/upload-new-file")
    public ResponseEntity<ApiResponse> addNewFile(@RequestParam("file") MultipartFile file) {
        FileEntity fileDB;
        try {
            fileDB =fileService.addNewFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(fileDB.getId(), "Uploaded the file successfully!! "));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse(null, "Could not upload the file!! "));
        }
    }*/
    @GetMapping(path = "/getAll")
    public List<FileDataDto> getAllFiles(){
        return fileService.getAllFiles();
    }

   /* @GetMapping("/get-all-files")
    public ResponseEntity<List<FileDataDto>> getListFiles() {
        return ResponseEntity.ok().body(fileService.getAllFiles());
    }*/


   /* @GetMapping(path = "/getById/{id}")               // getting only details of a file
    public FileEntity getFileById(@PathVariable String id){
        return fileService.getFileById(id);
    }*/

    @DeleteMapping(path = "/deleteById/{id}")
    public String deleteById(@PathVariable String id){
        return fileService.deleteById(id);
    }
    @DeleteMapping(path = "/deleteAll")
    public String deleteAll(){
        return fileService.deleteAll();
    }

    @GetMapping(path = "/getById/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable String id) {
        FileEntity fileDB = fileService.getFileById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDB.getContentType()))
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getOriginalFilename() + "\"")
                .body(fileDB.getBytes());
    }

   /* @GetMapping("/files/{fileId}")
    public ResponseEntity<byte[]> getFileById(@PathVariable String fileId) {
        return fileService.getFileById(fileId);
    }*/

}
