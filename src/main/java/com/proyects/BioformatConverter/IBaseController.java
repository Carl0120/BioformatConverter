package com.proyects.BioformatConverter;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public interface IBaseController {

     ModelAndView convert();
     ModelAndView upload(@RequestParam("file") MultipartFile file) throws Exception;
}
