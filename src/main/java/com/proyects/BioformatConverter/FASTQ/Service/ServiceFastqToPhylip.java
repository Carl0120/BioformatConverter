package com.proyects.BioformatConverter.FASTQ.Service;

import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.FASTA.Converter.LinkedToPhylipConverter;
import com.proyects.BioformatConverter.FASTQ.Converter.FasqToLinkedConverter;
import com.proyects.BioformatConverter.IFileService;
import com.proyects.BioformatConverter.Repository.PhylipRepository;
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
public class ServiceFastqToPhylip implements IFileService {
    @Autowired
    PhylipRepository phylipRepository;
    private final Path outputPath = Paths.get("files/output");
    @Override
    public String convert(MultipartFile file) throws Exception {

        FastqReader fastqReader = new SangerFastqReader();
        Iterable<Fastq> fastqIterable = fastqReader.read(file.getInputStream());

        LinkedHashMap<String, DNASequence> linkedHashMap =  FasqToLinkedConverter.convert(fastqIterable);
        PhylipIterable phylipIterable = LinkedToPhylipConverter.convert(linkedHashMap);

        Path outputFilePath = outputPath.resolve(phylipRepository.createExtension(Objects.requireNonNull(file.getOriginalFilename())));
        File outputFile = phylipRepository.copy(phylipIterable,outputFilePath);

        return outputFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {

        return phylipRepository.read(outputPath.resolve(fileName));
    }
}
