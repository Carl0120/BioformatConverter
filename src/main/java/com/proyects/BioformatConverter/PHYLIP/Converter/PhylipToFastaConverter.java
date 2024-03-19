package com.proyects.BioformatConverter.PHYLIP.Converter;

import com.proyects.BioformatConverter.Entity.PhylipIterable;
import com.proyects.BioformatConverter.Entity.PhylipSequence;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.genome.io.fastq.Fastq;

import java.util.LinkedHashMap;

public class PhylipToFastaConverter {
    public  static LinkedHashMap<String, DNASequence> convert(PhylipIterable phylipIterable) {
        LinkedHashMap<String, DNASequence> linkedHashMap = new LinkedHashMap<>();
        for (PhylipSequence sequence : phylipIterable.getSequences()){
            linkedHashMap.put(sequence.getKey(),sequence.getSequence());
        }
        return linkedHashMap;
    }
}
