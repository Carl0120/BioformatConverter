package com.proyects.BioformatConverter;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.net.MalformedURLException;

public interface IFileService {
     String convert(MultipartFile file) throws Exception;
     Resource load( String fileName ) throws MalformedURLException;

}
