package com.proyects.BioformatConverter.Configurations;

import com.proyects.BioformatConverter.Configurations.Routes;
import org.springframework.web.servlet.ModelAndView;

public class ModelFactory {
    public static ModelAndView createConvertView(String from, String to, String route){

        ModelAndView view = new ModelAndView(Routes.CONVERT);
        view.addObject("from", from);
        view.addObject("to",  to);
        view.addObject("route",route);
        return view;
    }
    public static ModelAndView createDownloadView(String fileName,String from, String to){
        ModelAndView view = new ModelAndView(Routes.DOWNLOAD);
        view.addObject("fileName", fileName);
        view.addObject("from", from);
        view.addObject("to", to);
        return view;
    }
}
