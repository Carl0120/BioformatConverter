package com.proyects.BioformatConverter.Domain;

import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.genome.io.fastq.Fastq;
import java.util.LinkedHashMap;


public class FasqToFastaConverter {

    public  static LinkedHashMap<String,DNASequence> convert(Iterable<Fastq> fastqs) {
        try {
            LinkedHashMap<String,DNASequence> fastaMap = new LinkedHashMap<>();
            for(Fastq fastq : fastqs) {
                fastaMap.put(fastq.getDescription(), new DNASequence(fastq.getSequence()));
                System.out.println("Segunda descripcion" + fastq.getDescription());
            }
            return fastaMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
