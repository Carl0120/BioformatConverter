package com.proyects.BioformatConverter.GENBANK.Service;

import com.proyects.BioformatConverter.IFileService;
import com.proyects.BioformatConverter.Repository.PhylipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceGenBankToPhylip implements IFileService {
    @Autowired
    PhylipRepository repository;

    private final Path inputPath = Paths.get("files/input");
    private final Path outputPath = Paths.get("files/output");


    @Override
    public String convert(MultipartFile file) throws Exception {
        //   Crea un archivo vacio en la ruta de entrada y transfiere el archivo rcivido del frontend a el archivo creado
        File genbankFile = new File(inputPath.resolve(Objects.requireNonNull(file.getOriginalFilename())).toUri());
        file.transferTo(genbankFile);

        //   El camino al archivo GenBank:
        File phylipFile = new File(outputPath.resolve(repository.createExtension(file.getOriginalFilename())).toUri());

        try {
            List<Sequence> sequences = parseGenBank(genbankFile);
            convertToPHYLIP(sequences, phylipFile);
            System.out.println("Conversión exitosa.");
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }
       return phylipFile.getName();
    }

    public static List<Sequence> parseGenBank(File filename) throws IOException {
        List<Sequence> sequences = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            StringBuilder sequenceData = new StringBuilder();
            String id = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("LOCUS")) {
                    id = line.split("\\s+")[1];
                } else if (line.startsWith("ORIGIN")) {
                    sequenceData = new StringBuilder();
                } else if (line.startsWith("//")) {
                    sequences.add(new Sequence(id, sequenceData.toString()));
                    id = null;
                } else if (line.matches("\\s*\\d+.*")) {
                    sequenceData.append(line.replaceAll("\\d", "").trim());
                }
            }
        }

        return sequences;
    }

    public static void convertToPHYLIP(List<Sequence> sequences, File filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            int sequenceLength = sequences.get(0).getSequence().length();
            int sequenceCount = sequences.size();

            writer.write(sequenceCount + " " + sequenceLength + "\n");

            for (Sequence seq : sequences) {
                writer.write(seq.getId() + "\t" + seq.getSequence() + "\n");
            }
        }
    }

    static class Sequence {
        private String id;
        private String sequence;

        public Sequence(String id, String sequence) {
            this.id = id;
            this.sequence = sequence;
        }

        public String getId() {
            return id;
        }

        public String getSequence() {
            return sequence;
        }
    }
    @Override
    public Resource load(String fileName) throws MalformedURLException {
        return this.repository.read(outputPath.resolve(fileName));
    }
}
