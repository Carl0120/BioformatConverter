package com.proyects.BioformatConverter.FASTA.Controller;

import com.proyects.BioformatConverter.IBaseController;
import com.proyects.BioformatConverter.Configurations.ModelFactory;
import com.proyects.BioformatConverter.FASTA.Service.ServiceFastaToPhylip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/fasta-to-phylip")
public class ControllerFastaToPhylip implements IBaseController {

    @Autowired
    ServiceFastaToPhylip fileService;
    @Override
    @GetMapping
    public ModelAndView convert() {
        return ModelFactory.createConvertView("FASTA", "PHYLIP", "fasta-to-phylip");
    }

    @Override
    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = this.fileService.convert(file);
        return  ModelFactory.createDownloadView(fileName,"FASTA","PHYLIP");
    }
}
