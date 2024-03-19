package com.proyects.BioformatConverter.FASTA.Service;

import com.proyects.BioformatConverter.FASTA.Converter.FastaToFastqConverter;
import com.proyects.BioformatConverter.Entity.FastqIterable;
import com.proyects.BioformatConverter.Repository.FastqRepository;
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
public class ServiceFastaToFastq implements IFileService {

    @Autowired
    FastqRepository fastqRepository;
    private final Path outputPath = Paths.get("files/output");
    @Override
    public String convert(MultipartFile multipartFile) throws Exception {

         LinkedHashMap<String, DNASequence > sequences = FastaReaderHelper.readFastaDNASequence(multipartFile.getInputStream());

        FastqIterable fastqIterable = FastaToFastqConverter.convertToFastq(sequences);

        Path outputFilePath = outputPath.resolve(fastqRepository.createExtension(Objects.requireNonNull(multipartFile.getOriginalFilename())));
        File outputFile = fastqRepository.copy(fastqIterable,outputFilePath);

         return outputFile.getName();
    }
    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return this.fastqRepository.read(outputPath.resolve(fileName));
    }

}
