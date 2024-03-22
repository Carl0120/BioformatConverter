package com.proyects.BioformatConverter.PHYLIP.Service;

import com.proyects.BioformatConverter.Configurations.Exceptions.ToGanBankConverter;
import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.FASTA.Converter.ToGenBankConverter;
import com.proyects.BioformatConverter.IFileService;
import com.proyects.BioformatConverter.PHYLIP.Converter.PhylipReader;
import com.proyects.BioformatConverter.PHYLIP.Converter.PhylipToLinkedConverter;
import com.proyects.BioformatConverter.Repository.FastaRepository;
import com.proyects.BioformatConverter.Repository.GenBankRepository;
import org.biojava.nbio.core.sequence.DNASequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

@Service
public class ServicePhylipToGenBank implements IFileService {

    @Autowired
    GenBankRepository genBankRepository;
    @Autowired
    FastaRepository fastaRepository;
    private final Path inputPath = Paths.get("files/input");
    private final Path outputPath = Paths.get("files/output");
    @Override
    public String convert(MultipartFile file) throws Exception {

        PhylipIterable phylipIterable = PhylipReader.read(file.getInputStream());
        LinkedHashMap<String, DNASequence> linkedHashMap = PhylipToLinkedConverter.convert(phylipIterable);

       File file1 =  fastaRepository.copy(linkedHashMap, inputPath.resolve(file.getOriginalFilename()));

        File outputFile = new File(outputPath.resolve(genBankRepository.createExtension(file.getOriginalFilename())).toUri());

        try (BufferedReader reader = new BufferedReader(new FileReader(file1));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            String line;
            StringBuilder description = new StringBuilder();
            StringBuilder sequence = new StringBuilder();


            while ((line = reader.readLine()) != null) {
                if (line.startsWith(">")) {
                    if (description.length() > 0) {
                        break;
                    }
                    description.append(line.substring(1)).append("\n");
                } else {
                    sequence.append(line.toUpperCase()).append("\n");
                }
            }
            String genBankFormat = ToGenBankConverter.formatToGenBank(description.toString(), sequence.toString());
            writer.print(genBankFormat);
            String genBankFormat2 = ToGanBankConverter.formatToGanBank(description.toString(), sequence.toString());
            writer.print(genBankFormat2);


        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return outputFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return this.genBankRepository.read(outputPath.resolve(fileName));
    }
}
