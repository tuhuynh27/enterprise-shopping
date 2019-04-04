package com.mrhmt.server.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/hello")
@CrossOrigin(origins="*")
public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "Hell Spring API";
    }
}
