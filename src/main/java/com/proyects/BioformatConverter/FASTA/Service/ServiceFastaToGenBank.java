package com.proyects.BioformatConverter.FASTA.Service;

import com.proyects.BioformatConverter.FASTA.Converter.ToGenBankConverter;
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
public class ServiceFastaToGenBank implements IFileService {

    @Autowired
    GenBankRepository genBankRepository;
    private final Path inputPath = Paths.get("files/input");
    private final Path outputPath = Paths.get("files/output");

    @Override
    public String convert(MultipartFile file) throws Exception {

        //   Crea un archivo vacio en la ruta de entrada y transfiere el archivo rcivido del frontend a el archivo creado
        File fileFasta = new File(inputPath.resolve(Objects.requireNonNull(file.getOriginalFilename())).toUri());
        file.transferTo(fileFasta);

        //   El camino al archivo GenBank:
        File outputFile = new File(outputPath.resolve(genBankRepository.createExtension(file.getOriginalFilename())).toUri());

        try (BufferedReader reader = new BufferedReader(new FileReader(fileFasta));
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

            return outputFile.getName();

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
