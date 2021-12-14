package com.example.acbun_rest;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestAPI {
    private List<Book> bookList = new ArrayList<>();

    @GetMapping(path="/books")
    public List<Book> GetAll(){
        //리스트에 있는 데이터를 모두 리턴하면 조회
        return bookList;
    }
    @PostMapping(path="/add")
    public String Add(@RequestBody Book book){
        bookList.add(book);
        return "Add";
    }
    @PutMapping(path="/update/{id}")
    public String update(@RequestBody Book tobook,@PathVariable int id){
        //수정은 책 생성해서 json 데이터 받는것과 + 책 삭제(id)를 받은 과정과 비슷하다
        Book find_book = bookList.stream()
                                .filter(book -> book.getId() == id)
                                .findAny()
                                .orElse(null);
        if(find_book!=null){
            find_book.setAuthor(tobook.getAuthor());
            find_book.setName(tobook.getName());
            find_book.setPrice(tobook.getPrice());
            return "success";
        }
        return "not valid";
    }
    //등록과 수정은 body가 json이 들어갑니다. <- @RequestBody 필요(json -> java object)
    @DeleteMapping(path="/delete/{id}")
    public String delete(@PathVariable int id){
        //삭제는 파라매터로 id에 해당하는 책을 리스트에서 제거함.
        //bookList를 Stream으로 변환하기.
        // filter : bookList의 요소를 하나씩 찾아가며 검사 (요소의 id가 파라매터로 넘어온 id랑 같냐??!)
        // 발견을했다면 findAny() : 한 개 요소에 대한 book 객체 가져오기
        //책에서는 findAny() : 조건에 맞는 첫번째 요소를 찾음
        // orElse() : 없다면 null 리턴
        Book find_book = bookList.stream()
                                 .filter(book -> book.getId() == id)
                                 .findAny()
                                 .orElse(null);
        if(find_book!=null){
            bookList.remove(find_book); //가져온 책 객체 제거
            return "success";
        }
        return "not valid";
    }
}
