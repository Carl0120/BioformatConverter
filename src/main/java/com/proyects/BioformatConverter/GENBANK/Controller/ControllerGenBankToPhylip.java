package com.proyects.BioformatConverter.GENBANK.Controller;

import com.proyects.BioformatConverter.Configurations.ModelFactory;
import com.proyects.BioformatConverter.Configurations.ServiceGenBankToPhylip;
import com.proyects.BioformatConverter.IBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/genbank-to-phylip")
public class ControllerGenBankToPhylip implements IBaseController {

    @Autowired
    ServiceGenBankToPhylip genBankToPhylip;

    @Override
    @GetMapping
    public ModelAndView convert() {
        return ModelFactory.createConvertView("GENBANK", "PHYLIP","/genbank-to-phylip");
    }

    @Override
    @PostMapping("/upload")
    public ModelAndView upload(MultipartFile file) throws Exception {
        String fileName = this.genBankToPhylip.convert(file);
        return  ModelFactory.createDownloadView(fileName,"GENBANK","PHYLIP");
    }
}
