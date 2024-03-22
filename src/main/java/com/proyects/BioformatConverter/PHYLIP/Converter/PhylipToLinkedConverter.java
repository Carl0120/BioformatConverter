package com.proyects.BioformatConverter.PHYLIP.Converter;

import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.Entity.PhylipSequence;
import org.biojava.nbio.core.sequence.DNASequence;

import java.util.LinkedHashMap;

public class PhylipToLinkedConverter {
    public  static LinkedHashMap<String, DNASequence> convert(PhylipIterable phylipIterable) {
        LinkedHashMap<String, DNASequence> linkedHashMap = new LinkedHashMap<>();
        for (PhylipSequence sequence : phylipIterable.getSequences()){
            linkedHashMap.put(sequence.getKey(),sequence.getSequence());
        }
        return linkedHashMap;
    }
}
