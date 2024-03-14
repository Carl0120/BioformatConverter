package com.proyects.BioformatConverter.Repository;

import com.proyects.BioformatConverter.Configurations.Enums.Extension;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaWriterHelper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
@Service
public class FastaRepository implements BaseRepository<LinkedHashMap<String, DNASequence>>{
    @Override
    public File copy(LinkedHashMap<String, DNASequence> object, Path inputPath) throws Exception {

            File file = new File(inputPath.toUri());
            FastaWriterHelper.writeNucleotideSequence(file, object.values());
            return file;
    }
    @Override
    public Resource read(Path filePath) throws MalformedURLException {

        return new UrlResource(filePath.toUri());
    }
    @Override
    public String createExtension(String name) {

        int index = name.lastIndexOf(".");
        name = name.substring(0,index).concat(Extension.FASTAShort.get());

        return name;
    }
}
