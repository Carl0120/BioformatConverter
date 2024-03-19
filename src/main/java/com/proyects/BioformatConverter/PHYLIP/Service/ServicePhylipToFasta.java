package com.proyects.BioformatConverter.PHYLIP.Service;

import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.IFileService;
import com.proyects.BioformatConverter.PHYLIP.Converter.PhylipReader;
import com.proyects.BioformatConverter.PHYLIP.Converter.PhylipToFastaConverter;
import com.proyects.BioformatConverter.Repository.FastaRepository;
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
import java.util.Objects;

@Service
public class ServicePhylipToFasta implements IFileService {
    @Autowired
    FastaRepository fastaRepository;
    private final Path outputPath = Paths.get("files/output");

    @Override
    public String convert(MultipartFile file) throws Exception {

        PhylipIterable phylipIterable = PhylipReader.read(file.getInputStream());
        LinkedHashMap<String, DNASequence> linkedHashMap = PhylipToFastaConverter.convert(phylipIterable);

        Path filePath =  outputPath.resolve(fastaRepository.createExtension(Objects.requireNonNull(file.getOriginalFilename())));
        File outputFile = fastaRepository.copy(linkedHashMap, filePath);
        return outputFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return fastaRepository.read(outputPath.resolve(fileName));
    }
}
