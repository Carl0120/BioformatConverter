package com.proyects.BioformatConverter.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhylipIterable {
    private int numberOfSequence = 0;
    private int lengthOfSequence = 0;
    private final List<PhylipSequence> sequences;

    public PhylipIterable(int lengthOfSequence, int numberOfSequence, List<PhylipSequence> sequences) throws IOException {
        this.sequences = new ArrayList<>();
        setLengthOfSequence(lengthOfSequence);
        setSequences(sequences,numberOfSequence);
    }
    public PhylipIterable(PhylipSequence phylipSequence) throws IOException {
        this.sequences = new ArrayList<>();
        addSequence(phylipSequence);
    }

    public int getLengthOfSequence() {
        return lengthOfSequence;
    }

    private void setLengthOfSequence(int lengthOfSequence) throws IOException {
        if (lengthOfSequence > 0) {
            this.lengthOfSequence = lengthOfSequence;
        } else {
            throw new IOException("El largo de la secuencia es invalido");
        }

    }

    public int getNumberOfSequence() {
        return numberOfSequence;
    }

    public  List<PhylipSequence> getSequences() {
        return sequences;
    }

    private void setSequences( List<PhylipSequence> sequences, int numberOfSequence) throws IOException {

        if ( sequences.isEmpty() || sequences.size() != numberOfSequence){
            throw new IOException("La Cantidad de secuencias no coincide con la indicada en el Archivo");
        }

        for (PhylipSequence entry : sequences) {
            addSequence(entry);
        }
    }
    public void addSequence(PhylipSequence portion) throws IOException {

        if(portion.getKey().length() > 10){
            throw new IOException("El identificador de secuencia debe tener longitud menor a 10 caracteres");
        }

        if (this.numberOfSequence == 0) {
            this.sequences.add(portion);
            this.lengthOfSequence = portion.getSequence().getLength();
        } else {
            if (portion.getSequence().getLength() == lengthOfSequence) {
                this.sequences.add(portion);
            } else {
                throw new IOException("La secuencias no estan alineadas");
            }
        }
        this.numberOfSequence++;
    }
}
