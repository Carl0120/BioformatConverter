package com.proyects.BioformatConverter.Repository;

import com.proyects.BioformatConverter.Configurations.Enums.Extension;
import com.proyects.BioformatConverter.GENBANK.GenBankEntry;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.sequence.features.FeatureInterface;
import org.biojava.nbio.core.sequence.template.AbstractSequence;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;

import java.util.List;
@Component
public class GenBankRepository implements BaseRepository<List<GenBankEntry>>{
    @Override
    public File copy(List<GenBankEntry> bankEntries, Path inputPath) throws Exception {

        return new File(inputPath.toUri());
    }

    @Override
    public Resource read(Path filePath) throws MalformedURLException {
        return new UrlResource(filePath.toUri());
    }

    @Override
    public String createExtension(String name) {
        int index = name.lastIndexOf(".");
        name = name.substring(0,index).concat(Extension.GENBANKShort.get());
        return name;
    }
}
