package com.proyects.BioformatConverter.GENBANK.Service;

import com.proyects.BioformatConverter.GENBANK.Converter.GenBankReader;
import com.proyects.BioformatConverter.GENBANK.Converter.GenBankToFastaConverter;
import com.proyects.BioformatConverter.GENBANK.GenBankEntry;
import com.proyects.BioformatConverter.IFileService;
import com.proyects.BioformatConverter.Repository.FastaRepository;
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
public class ServiceGenBankToFasta implements IFileService {
    @Autowired
    FastaRepository fastaRepository;
    private final Path inputPath = Paths.get("files/input");
    private final Path outputPath = Paths.get("files/output");
    @Override
    public String convert(MultipartFile file) throws Exception {

        List<GenBankEntry> genBankEntry = GenBankReader.read(file.getInputStream());
        LinkedHashMap<String,DNASequence> fastaMap = GenBankToFastaConverter.convert(genBankEntry);

        Path filePath =  outputPath.resolve(fastaRepository.createExtension(Objects.requireNonNull(file.getOriginalFilename())));
        File outputFile = fastaRepository.copy(fastaMap, filePath);

        return outputFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return fastaRepository.read(outputPath.resolve(fileName));
    }
}
