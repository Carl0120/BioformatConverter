package com.proyects.BioformatConverter.PHYLIP.Controller;

import com.proyects.BioformatConverter.Configurations.ModelFactory;
import com.proyects.BioformatConverter.IBaseController;
import com.proyects.BioformatConverter.PHYLIP.Service.ServicePhylipToFastq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
@RestController
@RequestMapping("/phylip-to-fastq")
public class ControllerPhylipToFastq implements IBaseController {
    @Autowired
    ServicePhylipToFastq phylipToFastq;
    @Override
    @GetMapping
    public ModelAndView convert() {
        return ModelFactory.createConvertView("PHYLIP", "FASTQ","/phylip-to-fastq");
    }

    @Override
    @PostMapping("/upload")
    public ModelAndView upload(MultipartFile file) throws Exception {
        String fileName = this.phylipToFastq.convert(file);
        return  ModelFactory.createDownloadView(fileName,"PHYLIP","FASTQ");
    }
}
