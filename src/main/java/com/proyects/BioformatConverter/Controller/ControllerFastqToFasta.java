package com.proyects.BioformatConverter.Controller;

import com.proyects.BioformatConverter.Configurations.Routes;
import com.proyects.BioformatConverter.Services.ServiceFastqToFasta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/fastq-to-fasta")
public class ControllerFastqToFasta implements IBaseController{
    @Autowired
    ServiceFastqToFasta fastqToFasta;
    @Override
    @GetMapping
    public ModelAndView convert() {
        ModelAndView view = new ModelAndView(Routes.CONVERT);
        view.addObject("from", "FASTQ");
        view.addObject("to", "FASTA");
        view.addObject("route","/fastq-to-fasta");
        return view;
    }

    @Override
    @PostMapping("/upload")
    public ModelAndView upload(MultipartFile file) throws Exception {

        String fileName = this.fastqToFasta.convert(file);
        ModelAndView view = new ModelAndView(Routes.DOWNLOAD);
        view.addObject("fileName", fileName);
        view.addObject("from", "FASTQ");
        view.addObject("to", "FASTA");
        return view;
    }
}
