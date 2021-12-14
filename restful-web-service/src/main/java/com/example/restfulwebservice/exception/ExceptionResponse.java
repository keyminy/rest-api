package com.example.restfulwebservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    //예외처리를 하기위해 사용되는 자바객체!
    private Date timestamp; //예외가 발생되는 시간
    private String message;
    private String details; //예외 상세정보
}
