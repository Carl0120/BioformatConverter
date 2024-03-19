package com.proyects.BioformatConverter.PHYLIP.Converter;

import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.Entity.PhylipSequence;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PhylipReader {

    public static PhylipIterable read(InputStream inputStream){
       int numberOfSequence;
       int lengthOfSequence;


        if (inputStream == null)
        {
            throw new IllegalArgumentException("inputStream must not be null");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            int[] i = getNumberAndLeng(reader);
            numberOfSequence = i[0];
            lengthOfSequence = i[1];

            List<PhylipSequence> sequenceList = getSequence(reader,numberOfSequence);

            return concatSequences(sequenceList,reader,numberOfSequence,lengthOfSequence);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static int[] getNumberAndLeng(BufferedReader reader) {
      try {
          String firstLine = reader.readLine().trim();
          String[] split = firstLine.split("\\s+");

          if (split.length == 2){

              int[] numbers = new int[2];
              numbers[0]= Integer.parseInt(split[0]);
              numbers[1]= Integer.parseInt(split[1]);
              return numbers;
      }else {
              throw new IOException("Formato de la primera linea del archivo incorrecto");
          }

      } catch (IOException | NumberFormatException e) {
          throw new RuntimeException(e);
      }
    }

    private static  List<PhylipSequence> getSequence(BufferedReader reader , int numberOfSequence) throws IOException {

      try {
          List<PhylipSequence> sequenceList = new ArrayList<>();
          for (int cont = 0; cont < numberOfSequence; cont++) {

              String[] line = reader.readLine().trim().split("\\s+");

              String id = line[0];
              StringBuilder sequence = new StringBuilder();

              for (int i = 1; i < line.length; i++) {
                  sequence.append(line[i]);
              }
              sequenceList.add(new PhylipSequence(id, new DNASequence(sequence.toString())));
          }
          return sequenceList;
      }catch (CompoundNotFoundException e){

          throw new RuntimeException("Error al leer la secuencia de ADN");
      }
    }

    private static PhylipIterable  concatSequences( List<PhylipSequence> sequenceList, BufferedReader reader,  int numberOfSequence, int lengthOfSequence) throws IOException {
        List<PhylipSequence> finalList = new ArrayList<>();
       try {
          int iterations = lengthOfSequence /50;

          String [] datos = new String[numberOfSequence * iterations];
          int contador = 0 ;

          String line;
           while ((line = reader.readLine()) != null) {

               if(!line.trim().isEmpty()){
                   datos[contador] = line.replace(" ","");
                   contador++;
               }
           }

           String [] fila = new String[numberOfSequence];
           for (int i = 0; i< numberOfSequence; i++){
               fila[i] = "";
               for (int e = 0;  e< numberOfSequence * iterations; e += numberOfSequence){
                   fila[i] = fila[i]+datos[e];
               }
           }

           for (int i = 0 ; i< numberOfSequence;i++){
               PhylipSequence phylipSequence = sequenceList.get(i);
               phylipSequence.modSequence(fila[i]);
               finalList.add(i,phylipSequence);
           }

          return new PhylipIterable(lengthOfSequence,numberOfSequence,finalList);
       }catch (IndexOutOfBoundsException e){
           throw new RuntimeException("La cantidad de secuencias no coincide con el numero especificado");
       }
       catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
}
