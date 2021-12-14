package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 3;

    //static block : 초기값 생성 (자바정석 p304)
    static {
        users.add(new User( 1, "mike", new Date()));
        users.add(new User( 2, "jacob", new Date()));
        users.add(new User( 3, "smith", new Date()));
    }
    //사용자 전체 목록 전달하는 메소드
    public List<User> findAll(){
        return users;
    }

    //사용자 추가
    public User save(User user){
        if(user.getId() == null){
            user.setId(++userCount);
        }//매개변수로 들어온 user의 getId가 존재할때
        users.add(user);
        return user;
    }

    //상세조회
    public User findOne(int id){
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        //id가 없으면 null반환
        return null;
        // M2스트림
        //return users.stream().filter(x->x.getId()==id).findFirst().orElseGet(null);
    }
    //삭제하기
    public User deleteById(int id){
        Iterator<User> ite = users.iterator();
        //iterator : 열거형
        while(ite.hasNext()){
            User user = ite.next();
            if(user.getId()==id){
                ite.remove(); //이터레이터에 데이터를 삭제하고 찾았던 user데이터를 반환함
                return user; //user가 지워진것을 알 수 있다.
            }
        }
        return null; //데이터를 찾지 못한 경우
    }
}
