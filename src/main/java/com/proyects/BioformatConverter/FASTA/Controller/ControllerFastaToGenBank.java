package com.proyects.BioformatConverter.FASTA.Controller;

import com.proyects.BioformatConverter.Configurations.ModelFactory;
import com.proyects.BioformatConverter.FASTA.Service.ServiceFastaToGenBank;
import com.proyects.BioformatConverter.IBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/fasta-to-genbank")
public class ControllerFastaToGenBank implements IBaseController {

    @Autowired
    ServiceFastaToGenBank fastaToGenBank;

    @Override
    @GetMapping
    public ModelAndView convert() {
        return ModelFactory.createConvertView("FASTA", "GENBANK","/fasta-to-genbank");
    }

    @Override
    @PostMapping("/upload")
    public ModelAndView upload(MultipartFile file) throws Exception {

        String fileName = this.fastaToGenBank.convert(file);
        return  ModelFactory.createDownloadView(fileName,"FASTA","GENBANK");
    }
}
