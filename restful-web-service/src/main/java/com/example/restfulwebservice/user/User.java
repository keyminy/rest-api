package com.example.restfulwebservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    private Integer id; //int면 user.getId() == null 비교 못하는구나
    @Size(min=2,message = "Name은 2글자 이상 입력해주세요")
    private String name;
    @Past
    private Date joinDate;
}
