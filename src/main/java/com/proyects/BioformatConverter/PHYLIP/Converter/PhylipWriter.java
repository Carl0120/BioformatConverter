package com.proyects.BioformatConverter.PHYLIP.Converter;

import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.Entity.PhylipSequence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class PhylipWriter {

    public static void write(PhylipIterable object, File outputFile){
        int  last = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
              writer.write(" "+ object.getNumberOfSequence()+" "+object.getLengthOfSequence());
              writer.newLine();

            // Escribe cada secuencia alineada en el formato PHYLIP
            for (PhylipSequence entry : object.getSequences()) {
                writer.write(String.format("%-10s", entry.getKey()));
                String sequence = entry.getSequence().getSequenceAsString();
                writer.write(" ");
                for (int i = last; i < Math.min(50, object.getLengthOfSequence()); i += 10) {

                    writer.write(" " + sequence.substring(i, Math.min(i + 10, Math.min(50, object.getLengthOfSequence()))));
                }
                writer.newLine();
            }
            last = Math.min(50, object.getLengthOfSequence());
            writer.newLine();


                   while (last < object.getLengthOfSequence()) {
                       for (PhylipSequence entry : object.getSequences()) {
                           String sequence = entry.getSequence().getSequenceAsString();
                           int length = sequence.length();
                           writer.write("              ", 0, 11);

                           for (int i = last; i < Math.min(last +50 , object.getLengthOfSequence()); i += 10) {
                               writer.write(" " + sequence.substring(i, Math.min(i + 10, Math.min(last +50, length))));
                           }

                           writer.newLine();
                       }
                       writer.newLine();
                       last += 50;
                   }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
