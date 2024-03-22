package com.proyects.BioformatConverter.GENBANK.Controller;

import com.proyects.BioformatConverter.Configurations.ModelFactory;
import com.proyects.BioformatConverter.GENBANK.Service.ServiceGenBankToFasta;
import com.proyects.BioformatConverter.IBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/genbank-to-fasta")
public class ControllerBenBankToFasta implements IBaseController {
    @Autowired
    ServiceGenBankToFasta bankToFasta;
    @Override
    @GetMapping
    public ModelAndView convert() {
       return ModelFactory.createConvertView("GENBANK", "FASTA","/genbank-to-fasta");
    }

    @Override
    @PostMapping("/upload")
    public ModelAndView upload(MultipartFile file) throws Exception {
        String fileName = this.bankToFasta.convert(file);
        return  ModelFactory.createDownloadView(fileName,"GENBANK","FASTA");
    }
}
