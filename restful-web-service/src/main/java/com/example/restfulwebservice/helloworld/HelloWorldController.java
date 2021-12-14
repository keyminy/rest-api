package com.example.restfulwebservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    //GET
    // URI : /hello-world (endpoint)
    @GetMapping(path = "/hello-world")
    public String helloWolrd(){
        return "Hello World";
    }
        //2번쨰 .. Java Bean형태로 반환시켜 보자. -> 값을 JSON형태로 변환해서 반환해준다.!!
    // alt + enter
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWolrdBean(){
        return new HelloWorldBean("Hello World");
    }
    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World!,%s",name));
    }

    //다국어처리 테스트
    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(
        @RequestHeader(name = "Accept-Language",required = false) Locale locale){
        return messageSource.getMessage("greeting.message",null,locale);
    }
}
