package com.thrymr.hotel.management.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileDataDto {
    private String id;
    private String contentType;
    private String originalFilename;
    private String  url;
    private byte[] bytes;

}
