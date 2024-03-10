package com.proyects.BioformatConverter.Services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IFileService {
    public String convert(MultipartFile file) throws IOException;
    public Resource load( String fileName ) throws MalformedURLException;

}
