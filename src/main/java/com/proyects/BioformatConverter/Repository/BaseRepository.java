package com.proyects.BioformatConverter.Repository;

import org.springframework.core.io.Resource;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface BaseRepository<I> {

     File copy(I object, Path inputPath) throws Exception;

     Resource read(Path filePath) throws MalformedURLException;
     String createExtension(String name);
}
