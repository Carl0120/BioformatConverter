package com.proyects.BioformatConverter.Domain;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface IConverter<I,O> {

    public O convertTo(MultipartFile file) throws IOException;

    public String rename(MultipartFile input);
    public I getImage(MultipartFile file) throws IOException;
}
