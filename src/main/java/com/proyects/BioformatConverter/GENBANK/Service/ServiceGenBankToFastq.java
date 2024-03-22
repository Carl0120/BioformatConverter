package com.proyects.BioformatConverter.GENBANK.Service;

import com.proyects.BioformatConverter.Entity.FastqIterable;
import com.proyects.BioformatConverter.FASTA.Converter.LinkedToFastqConverter;
import com.proyects.BioformatConverter.GENBANK.Converter.GenBankReader;
import com.proyects.BioformatConverter.GENBANK.Converter.GenBankToFastaConverter;
import com.proyects.BioformatConverter.GENBANK.GenBankEntry;
import com.proyects.BioformatConverter.IFileService;
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
import java.util.List;
import java.util.Objects;

@Service
public class ServiceGenBankToFastq implements IFileService {
    @Autowired
    FastqRepository fastqRepository;
    private final Path outputPath = Paths.get("files/output");

    @Override
    public String convert(MultipartFile file) throws Exception {

        List<GenBankEntry> genBankEntry = GenBankReader.read(file.getInputStream());
        LinkedHashMap<String, DNASequence> fastaMap = GenBankToFastaConverter.convert(genBankEntry);

        FastqIterable fastqIterable = LinkedToFastqConverter.convertToFastq(fastaMap);

        Path outputFilePath = outputPath.resolve(fastqRepository.createExtension(Objects.requireNonNull(file.getOriginalFilename())));
        File outputFile = fastqRepository.copy(fastqIterable,outputFilePath);

        return outputFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return this.fastqRepository.read(outputPath.resolve(fileName));
    }
}
