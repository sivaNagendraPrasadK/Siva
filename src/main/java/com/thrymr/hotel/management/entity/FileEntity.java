package com.thrymr.hotel.management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FILES")
public class FileEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String originalFilename;
    private String contentType;
    private String  url;
    private byte[] bytes;

    public FileEntity(String originalFilename, String contentType, byte[] bytes) {
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.bytes = bytes;
    }


}
