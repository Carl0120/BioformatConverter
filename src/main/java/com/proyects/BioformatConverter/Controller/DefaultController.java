package com.proyects.BioformatConverter.Controller;

import com.proyects.BioformatConverter.Configurations.Routes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@RestController
@RequestMapping("")
public class DefaultController {

    @GetMapping
    public ModelAndView index(){
        return new ModelAndView(Routes.HOME);
    }
}
