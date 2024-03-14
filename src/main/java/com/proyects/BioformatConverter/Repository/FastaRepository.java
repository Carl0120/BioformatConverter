package com.proyects.BioformatConverter.Repository;

import com.proyects.BioformatConverter.Configurations.Enums.Extension;
import org.biojava.nbio.core.sequence.DNASequence;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
@Component
public class FastaRepository implements BaseRepository<LinkedHashMap<String, DNASequence>>{
    @Override
    public File copy(LinkedHashMap<String, DNASequence> object, Path inputPath) throws Exception {

        File file = new File(inputPath.toUri());
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String header : object.keySet()){
                writer.write(">"+ header+"\n");
                String sequence = object.get(header).getSequenceAsString();
                int maxLength = 120;
                int lenght = sequence.length();
                int startIndex = 0;
                while (startIndex < lenght){
                    int endIndex = Math.min(startIndex+maxLength, lenght);
                    String line = sequence.substring(startIndex,endIndex);
                    writer.write(line);
                    writer.newLine();
                    startIndex += maxLength;
                }
            }
        }
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
