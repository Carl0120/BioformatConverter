package com.proyects.BioformatConverter.Configurations;

import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.FASTA.Converter.LinkedToPhylipConverter;
import com.proyects.BioformatConverter.GENBANK.Converter.GenBankReader;
import com.proyects.BioformatConverter.GENBANK.Converter.GenBankToFastaConverter;
import com.proyects.BioformatConverter.GENBANK.GenBankEntry;
import com.proyects.BioformatConverter.IFileService;
import com.proyects.BioformatConverter.Repository.PhylipRepository;
import org.biojava.nbio.core.sequence.DNASequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
@Service
public class ServiceGenBankToPhylip implements IFileService {
    @Autowired
    PhylipRepository phylipRepository;
    private final Path outputPath = Paths.get("files/output");

    @Override
    public String convert(MultipartFile file) throws Exception {

        List<GenBankEntry> genBankEntry = GenBankReader.read(file.getInputStream());
        LinkedHashMap<String, DNASequence> linkedHashMap = GenBankToFastaConverter.convert(genBankEntry);

        PhylipIterable phylipIterable = LinkedToPhylipConverter.convert(linkedHashMap);

        Path outputFilePath = outputPath.resolve(phylipRepository.createExtension(Objects.requireNonNull(file.getOriginalFilename())));
        File outputFile = phylipRepository.copy(phylipIterable,outputFilePath);

        return outputFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return this.phylipRepository.read(outputPath.resolve(fileName));
    }
}
