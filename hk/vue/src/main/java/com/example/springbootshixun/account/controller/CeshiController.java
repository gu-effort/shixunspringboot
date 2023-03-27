package com.example.springbootshixun.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CeshiController {
    @GetMapping ("/gu")
    public String ceshi(){
        return "hello word";
    }
}
