package com.example.acbun_rest;

import lombok.Data;

@Data
public class Book {
    private int id;
    private String name;
    private String author;
    private int price;
}
