package com.example.restfulwebservice.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class UserController {
    private UserDaoService service;
    //생성자를 통해 의존성 주입 : 생성자의 매개변수를 통해서 전달하고자하는 객체의 인스턴스선언
    //UserDaoService에 @Service 붙여줘야 오류가 사라진다..(빈을 만들어줘야징..)
    public UserController(UserDaoService service){
        this.service = service;
    }

    //1.전체 사용자 조회
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    //2.사용자 한명 조회 : 매개변수 : 사용자가 몇번 id를 가지고 있는지??
    // /users/1 or /users/10 : return null이 나옴
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
         User user = service.findOne(id);
         if(user == null){
             throw new UserNotFoundException(String.format("ID[%s] not found",id));
         }

         //HATEOAS 넣기
        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(
                methodOn(this.getClass()).retrieveAllUsers()
        );
        model.add(linkTo.withRel("all-users"));
        return model;
    }

    //3.사용자 추가
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        //전달되는 user JSON포맷 request parameter를 service의 save메소드에 전달하면 저장됨
        User savedUser = service.save(user);
        //사용자에게 요청값을 나타내주기 위해서..
        //fromCurrentRequest() : 현재 요청되어진 request값을 사용한다.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                   .path("/{id}")
                                   .buildAndExpand(savedUser.getId())
                                   .toUri();
        return ResponseEntity.created(location).build();
        //Status : 201Created 상태코드를 전달받게됨.
    }

    //4.사용자 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
    }

    //사용자 개별조회

}
