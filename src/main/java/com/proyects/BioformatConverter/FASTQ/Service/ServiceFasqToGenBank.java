package com.proyects.BioformatConverter.FASTQ.Service;

import com.proyects.BioformatConverter.IFileService;
import com.proyects.BioformatConverter.Repository.GenBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ServiceFasqToGenBank implements IFileService {

    @Autowired
    GenBankRepository genBankRepository;
    private final Path inputPath = Paths.get("files/input");
    private final Path outputPath = Paths.get("files/output");

    @Override
    public String convert(MultipartFile file) throws Exception {

        //   Crea un archivo vacio en la ruta de entrada y transfiere el archivo rcivido del frontend a el archivo creado
        File inputFastqFile = new File(inputPath.resolve(Objects.requireNonNull(file.getOriginalFilename())).toUri());
        file.transferTo(inputFastqFile);

        //   El camino al archivo GenBank:
        File outputGenBankFile = new File(outputPath.resolve(genBankRepository.createExtension(file.getOriginalFilename())).toUri());

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFastqFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputGenBankFile))) {

            String line;
            int recordCount = 0;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("@")) {
                    String[] headerParts = line.split(" ");
                    String sequenceHeader = headerParts[0].substring(1);
                    String header = line.substring(1);
                    String sequence = reader.readLine();
                    reader.readLine();

                    writer.write("LOCUS       " + sequenceHeader + "             " + sequence.length() + " bp    DNA              \n");
                    writer.write("DEFINITION " + header + "\n");
                    writer.write("ACCESSION   " + sequenceHeader.split(" ")[0] + "\n");
                    writer.write("VERSION     " + sequenceHeader + "\n");
                    writer.write("SOURCE Unknown\n");
                    writer.write("FEATURES             Location/Qualifiers\n");
                    writer.write("ORIGIN\n");

                    int i = 0;
                    while (i < sequence.length()) {
                        int end = Math.min(i + 60, sequence.length());
                        String subsequence = sequence.substring(i, end);
                        StringBuilder formattedSubsequence = new StringBuilder();
                        for (int j = 0; j < subsequence.length(); j++) {
                            formattedSubsequence.append(subsequence.charAt(j));
                            if ((j + 1) % 10 == 0 && (j + 1) != subsequence.length()) {
                                formattedSubsequence.append(" ");
                            }
                        }
                        writer.write("         " + (i + 1) + " " + formattedSubsequence.toString() + "\n");
                        i = end;
                    }
                    writer.write("//\n");
                    recordCount++;
                }
            }

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return outputGenBankFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return this.genBankRepository.read(outputPath.resolve(fileName));
    }
}
