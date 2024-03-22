package com.proyects.BioformatConverter.GENBANK.Converter;
import com.proyects.BioformatConverter.GENBANK.GenBankEntry;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenBankReader {

    public static  List<GenBankEntry> read(InputStream inputStream){
        List<GenBankEntry> entries = new ArrayList<>();

        if (inputStream == null)
        {
            throw new IllegalArgumentException("inputStream must not be null");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.mark(1000);
            String linea = "";
            while ((linea = reader.readLine()) != null && !linea.equals("")) {
                int i = 1 ;
                GenBankEntry bankEntry;
                bankEntry =  readCabesera(linea);
                bankEntry.definition = readDefinition(reader);
                bankEntry.setOrigin(readSequence(reader));
                i++;
                entries.add(bankEntry);

            }





          return entries;
        } catch (IOException | CompoundNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    private static GenBankEntry readCabesera(String reader) throws IOException {

        GenBankEntry bankEntry = new GenBankEntry();
        String[] firstLine = reader.trim().split("\\s+");
        if (Objects.equals(firstLine[0], "LOCUS")){
            bankEntry.locus.id = firstLine[1];
            bankEntry.locus.lengthOfSequence = firstLine[2];
            return bankEntry;
        }else {
            throw new IOException("Formato se primera Linea Incorrecto");
        }
    }
    private static String readDefinition(BufferedReader reader) throws IOException {

        StringBuilder definition = new StringBuilder(reader.readLine());

        if (definition.toString().startsWith("DEFINITION")){
            reader.mark(1000);
            String linea = "";
            while ((linea = reader.readLine()).startsWith(" ")) {
                reader.mark(1000);
                definition.append(" ").append(linea.trim());
            }
            reader.reset();
            return definition.delete(0,10).toString().trim();
        }else {
            throw new IOException("Formato de definicion Incorrecto");
        }

    }

    private static DNASequence readSequence(BufferedReader reader) throws IOException, CompoundNotFoundException {
        StringBuilder sequenceString = new StringBuilder();

        String linea = "";
        while (!(linea = reader.readLine()).startsWith("ORIGIN")) {
            System.out.println(linea);
        }
        System.out.println(linea);
        while (!(linea = reader.readLine()).startsWith("//")) {
            linea = linea.trim();
            System.out.println(linea);
            sequenceString.append(linea.substring(linea.indexOf(" ")).trim().replaceAll(" ", ""));
        }
        return new DNASequence(sequenceString.toString().toUpperCase());
    }
}
