package com.thrymr.hotel.management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class ApiResponse {

    private int statusCode;

    //private  String fileId;
    private String message;


    private Object data;
   // private Object fileData;
    public ApiResponse(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
    public ApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    public ApiResponse(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        //this.fileData=fileDta;
    }

  /*  public ApiResponse(String fileId, String message) {
        this.fileId = fileId;
        this.message = message;
    }*/
}
