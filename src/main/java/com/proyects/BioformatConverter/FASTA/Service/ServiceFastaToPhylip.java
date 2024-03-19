package com.proyects.BioformatConverter.FASTA.Service;

import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.FASTA.Converter.FastaToPhylipConverter;
import com.proyects.BioformatConverter.Repository.PhylipRepository;
import com.proyects.BioformatConverter.IFileService;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
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
public class ServiceFastaToPhylip implements IFileService {

    @Autowired
    PhylipRepository phylipRepository;
    private final Path outputPath = Paths.get("files/output");
    @Override
    public String convert(MultipartFile file) throws Exception {
        LinkedHashMap<String, DNASequence> sequences = FastaReaderHelper.readFastaDNASequence(file.getInputStream());

        PhylipIterable phylipIterable = FastaToPhylipConverter.convert(sequences);

        Path outputFilePath = outputPath.resolve(phylipRepository.createExtension(Objects.requireNonNull(file.getOriginalFilename())));
        File outputFile = phylipRepository.copy(phylipIterable,outputFilePath);

        return outputFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return this.phylipRepository.read(outputPath.resolve(fileName));
    }
}
