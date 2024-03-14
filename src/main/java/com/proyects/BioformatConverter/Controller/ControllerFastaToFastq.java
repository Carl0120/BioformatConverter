package com.proyects.BioformatConverter.Controller;

import com.proyects.BioformatConverter.Configurations.Routes;
import com.proyects.BioformatConverter.Services.ServiceFastaToFastq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/fasta-to-fastq")
public class ControllerFastaToFastq implements IBaseController{
    @Autowired
    ServiceFastaToFastq fileService;

    @GetMapping
    public ModelAndView convert(){
        ModelAndView view = new ModelAndView(Routes.CONVERT);
        view.addObject("from", "FASTA");
        view.addObject("to", "FASTQ");
        view.addObject("route","/fasta-to-fastq");
        return view;
    }
    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("file") MultipartFile file) throws Exception {

        String fileName = this.fileService.convert(file);
        ModelAndView view = new ModelAndView(Routes.DOWNLOAD);
        view.addObject("fileName", fileName);
        view.addObject("from", "FASTA");
        view.addObject("to", "FASTQ");
        return view;
    }
}
