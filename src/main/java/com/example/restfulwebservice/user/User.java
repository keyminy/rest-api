package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value={"password","ssn"})
@NoArgsConstructor //default생성자 생성
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {
    private Integer id; //int면 user.getId() == null 비교 못하는구나
    @Size(min=2,message = "Name은 2글자 이상 입력해주세요")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String name;
    @Past //가입날짜는 과거데이터 이하만 가능,미래불가!
    @ApiModelProperty(notes = "사용자의 등록일을 입력해 주세요")
    private Date joinDate;
    @ApiModelProperty(notes = "사용자의 패스워드를 입력해 주세요")
    private String password;
    @ApiModelProperty(notes = "사용자의 주민번호를 입력해 주세요")
    private String ssn;
}
