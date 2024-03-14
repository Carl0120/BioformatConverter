package com.proyects.BioformatConverter.Api;

import com.proyects.BioformatConverter.Services.ServiceFastaToFastq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@RestController
@RequestMapping("api")
public class FileControllerApi {
    @Autowired
    ServiceFastaToFastq fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws Exception {

        String fileName = this.fileService.convert(file);
        return ResponseEntity.ok(fileName);
    }

        @GetMapping("/download/{id}")
        public ResponseEntity<Resource> load(@PathVariable String id) throws MalformedURLException {

        Resource resource = this.fileService.load(id);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
}
