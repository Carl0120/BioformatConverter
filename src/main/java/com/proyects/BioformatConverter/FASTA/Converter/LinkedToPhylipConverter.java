package com.proyects.BioformatConverter.FASTA.Converter;

import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.Entity.PhylipSequence;
import org.biojava.nbio.core.sequence.DNASequence;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedToPhylipConverter {

    public static PhylipIterable convert(LinkedHashMap<String, DNASequence> sequences) {
        try {
            PhylipIterable phylipIterable = null;

            for (Map.Entry<String, DNASequence> entry : sequences.entrySet()) {
                PhylipSequence portion = new PhylipSequence(entry.getKey(), entry.getValue());

                if (phylipIterable == null) {
                    phylipIterable = new PhylipIterable(portion);
                } else {
                    phylipIterable.addSequence(portion);
                }
            }
            return phylipIterable;
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }
    }