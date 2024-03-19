package com.proyects.BioformatConverter.Entity;

import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;

public class PhylipSequence {
    private String key;
    private DNASequence sequence;

    public PhylipSequence(String description, DNASequence sequence) {
        addKey(description);
        this.sequence = sequence;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DNASequence getSequence() {
        return sequence;
    }

    public void setSequence(DNASequence sequence) {
        this.sequence = sequence;
    }
    public void modSequence(String s)  {
        try {
            sequence = new DNASequence(sequence.getSequenceAsString()+s);
        } catch (CompoundNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void addKey(String description){

        if(description.length() > 0 && description.length() <= 10){
            this.key = description;
        }else {
            int index = description.indexOf(" ");
            if(index == -1 || index > 10){
                this.key = description.substring(0,10);
            }else{
                this.key = description.substring(0,index);
            }
        }
    }
}
