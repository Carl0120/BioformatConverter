package com.proyects.BioformatConverter.PHYLIP.Controller;

import com.proyects.BioformatConverter.Configurations.ModelFactory;
import com.proyects.BioformatConverter.IBaseController;
import com.proyects.BioformatConverter.PHYLIP.Service.ServicePhylipToFasta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/phylip-to-fasta")
public class ControllerPhylipToFasta implements IBaseController {
   @Autowired
   ServicePhylipToFasta phylipToFasta;
    @Override
    @GetMapping
    public ModelAndView convert() {
        return ModelFactory.createConvertView("PHYLIP", "FASTA","/phylip-to-fasta");
    }

    @Override
    @PostMapping("/upload")
    public ModelAndView upload(MultipartFile file) throws Exception {
        String fileName = this.phylipToFasta.convert(file);
        return  ModelFactory.createDownloadView(fileName,"PHYLIP","FASTA");
    }
}
