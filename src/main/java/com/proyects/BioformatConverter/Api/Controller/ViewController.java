package com.proyects.BioformatConverter.Api.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class ViewController {

    @GetMapping
    public String get(){
        return "todo ok";
    }
}
