package com.proyects.BioformatConverter.FASTQ.Service;

import com.proyects.BioformatConverter.FASTQ.Converter.FasqToLinkedConverter;
import com.proyects.BioformatConverter.Repository.FastaRepository;
import com.proyects.BioformatConverter.IFileService;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.genome.io.fastq.Fastq;
import org.biojava.nbio.genome.io.fastq.FastqReader;
import org.biojava.nbio.genome.io.fastq.SangerFastqReader;
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
public class ServiceFastqToFasta implements IFileService {

    @Autowired
    FastaRepository fastaRepository;
    private final Path outputPath = Paths.get("files/output");
    @Override
    public String convert(MultipartFile file) throws Exception {

        FastqReader fastqReader = new SangerFastqReader();
        Iterable<Fastq> fastqIterable = fastqReader.read(file.getInputStream());


        LinkedHashMap<String, DNASequence> linkedHashMap =  FasqToLinkedConverter.convert(fastqIterable);

        Path filePath =  outputPath.resolve(fastaRepository.createExtension(Objects.requireNonNull(file.getOriginalFilename())));
        File outputFile = fastaRepository.copy(linkedHashMap, filePath);

      return outputFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return fastaRepository.read(outputPath.resolve(fileName));
    }
}
