package com.proyects.BioformatConverter.Repository;

import com.proyects.BioformatConverter.Configurations.Enums.Extension;
import com.proyects.BioformatConverter.Domain.FastqIterable;
import org.biojava.nbio.genome.io.fastq.FastqWriter;
import org.biojava.nbio.genome.io.fastq.SangerFastqWriter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@Service
public class FastqRepository implements BaseRepository<FastqIterable> {
    @Override
    public File copy(FastqIterable object, Path path) throws IOException {

        File file = new File(path.toUri());
        FastqWriter fastqWriter = new SangerFastqWriter();
        fastqWriter.write(file,object);
        return file;
    }
    @Override
    public Resource read(Path filePath) throws MalformedURLException {
        return new UrlResource(filePath.toUri());
    }
    @Override
    public String createExtension(String name) {

        int index = name.lastIndexOf(".");
        name = name.substring(0,index).concat(Extension.FASTQShort.get());

        return name;
    }
}
