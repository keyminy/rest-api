package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;
    //생성자를 통해 의존성 주입 : 생성자의 매개변수를 통해서 전달하고자하는 객체의 인스턴스선언
    //UserDaoService에 @Service 붙여줘야 오류가 사라진다..(빈을 만들어줘야징..)
    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    //1.전체 사용자 조회
    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){

        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password","ssn");//매개변수 : 포함시키고자 하는 필터값

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    //2.사용자 한명 조회 : 매개변수 : 사용자가 몇번 id를 가지고 있는지??
    // /users/1 or /users/10 : return null이 나옴
    //@GetMapping("/v1/users/{id}")
    //@GetMapping(value = "/users/{id}/",params = "version=1")
    //@GetMapping(value = "/users/{id}",headers = "X-API-VERSION=1")
    @GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
         User user = service.findOne(id);
         if(user == null){
             throw new UserNotFoundException(String.format("ID[%s] not found",id));
         }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password","ssn");//매개변수 : 포함시키고자 하는 필터값

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }
    //v2
    // /users/1 or /users/10 : return null이 나옴
    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}/",params = "version=2")
    //@GetMapping(value = "/users/{id}",headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
         User user = service.findOne(id);
         if(user == null){
             throw new UserNotFoundException(String.format("ID[%s] not found",id));
         }

         /* User -> UserV2 copy하는 방법*/
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password","ssn","grade");//매개변수 : 포함시키고자 하는 필터값

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }


}
