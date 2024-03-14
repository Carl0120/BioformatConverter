package com.proyects.BioformatConverter.Controller;

import com.proyects.BioformatConverter.Configurations.Routes;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public interface IBaseController {

    public ModelAndView convert();
    public ModelAndView upload(@RequestParam("file") MultipartFile file) throws Exception;
}
