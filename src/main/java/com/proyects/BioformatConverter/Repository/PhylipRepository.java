package com.proyects.BioformatConverter.Repository;

import com.proyects.BioformatConverter.Configurations.Enums.Extension;
import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.PHYLIP.Converter.PhylipWriter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;

@Component
public class PhylipRepository implements BaseRepository<PhylipIterable>{
    @Override
    public File copy(PhylipIterable object, Path inputPath) throws Exception {
        File outputFile = new File(inputPath.toUri());
        PhylipWriter.write(object, outputFile);
        return outputFile;
    }

    @Override
    public Resource read(Path filePath) throws MalformedURLException {
        return new UrlResource(filePath.toUri());
    }

    @Override
    public String createExtension(String name) {
        int index = name.lastIndexOf(".");
        name = name.substring(0,index).concat(Extension.PHYLIPShort.get());
        return name;
    }
}
