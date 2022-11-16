package com.example.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //404 Not Found
public class UserNotFoundException extends RuntimeException {
    //RuntimeException : 실행 시 오류
    public UserNotFoundException(String message) {
        super(message);
        //생성자 : 부모클래스에서 전달받은 메시지를 반환시킴
    }
}
