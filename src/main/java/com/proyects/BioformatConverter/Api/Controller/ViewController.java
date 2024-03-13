package com.proyects.BioformatConverter.Api.Controller;

import com.proyects.BioformatConverter.Configurations.Routes;
import com.proyects.BioformatConverter.Services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("")
public class ViewController {
    @Autowired
    FileService fileService;
    @GetMapping
    public ModelAndView index(){
        return new ModelAndView(Routes.HOME);
    }
    @GetMapping
    @RequestMapping("/fasta-to-fastq")
    public ModelAndView ToFastq(){
        ModelAndView view = new ModelAndView(Routes.CONVERT);
        view.addObject("from", "FASTA");
        view.addObject("to", "FASTQ");
        return view;
    }
    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam("file") MultipartFile file) throws IOException {

        String fileName = this.fileService.convert(file);
        ModelAndView view = new ModelAndView(Routes.DOWNLOAD);
        view.addObject("fileName", fileName);
        view.addObject("from", "FASTA");
        view.addObject("to", "FASTQ");
        return view;
    }
}
