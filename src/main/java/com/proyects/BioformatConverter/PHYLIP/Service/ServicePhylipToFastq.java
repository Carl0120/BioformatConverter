package com.proyects.BioformatConverter.PHYLIP.Service;

import com.proyects.BioformatConverter.Entity.FastqIterable;
import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.FASTA.Converter.FastaToFastqConverter;
import com.proyects.BioformatConverter.IFileService;
import com.proyects.BioformatConverter.PHYLIP.Converter.PhylipReader;
import com.proyects.BioformatConverter.PHYLIP.Converter.PhylipToFastaConverter;
import com.proyects.BioformatConverter.Repository.FastqRepository;
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
import java.util.Objects;

@Service
public class ServicePhylipToFastq implements IFileService {

    @Autowired
    FastqRepository fastqRepository;

    private final Path outputPath = Paths.get("files/output");
    @Override
    public String convert(MultipartFile file) throws Exception {

        PhylipIterable phylipIterable = PhylipReader.read(file.getInputStream());
        LinkedHashMap<String, DNASequence> linkedHashMap = PhylipToFastaConverter.convert(phylipIterable);
        FastqIterable fastqIterable =   FastaToFastqConverter.convertToFastq(linkedHashMap);

        Path filePath =  outputPath.resolve(fastqRepository.createExtension(Objects.requireNonNull(file.getOriginalFilename())));
        File outputFile = fastqRepository.copy(fastqIterable, filePath);
        return outputFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return fastqRepository.read(outputPath.resolve(fileName));
    }
}
