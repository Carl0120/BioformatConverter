package com.proyects.BioformatConverter.FASTQ.Controller;

import com.proyects.BioformatConverter.Configurations.ModelFactory;
import com.proyects.BioformatConverter.FASTQ.Service.ServiceFasqToGenBank;
import com.proyects.BioformatConverter.IBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/fastq-to-genbank")
public class ControllerFastqToGenBank implements IBaseController {

    @Autowired
    ServiceFasqToGenBank fasqToGenBank;

    @Override
    @GetMapping
    public ModelAndView convert() {
        return ModelFactory.createConvertView("FASTQ", "GENBANK","/fastq-to-genbank");
    }

    @Override
    @PostMapping("/upload")
    public ModelAndView upload(MultipartFile file) throws Exception {
        String fileName = this.fasqToGenBank.convert(file);
        return  ModelFactory.createDownloadView(fileName,"FASTQ","GENBANK");
    }
}
