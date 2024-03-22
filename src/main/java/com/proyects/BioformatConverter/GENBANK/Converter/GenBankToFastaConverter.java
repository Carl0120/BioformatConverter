package com.proyects.BioformatConverter.GENBANK.Converter;

import com.proyects.BioformatConverter.GENBANK.GenBankEntry;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.genome.io.fastq.Fastq;

import java.util.LinkedHashMap;
import java.util.List;

public class GenBankToFastaConverter {

    public  static LinkedHashMap<String, DNASequence> convert(List<GenBankEntry> entries) {
        try {
            LinkedHashMap<String,DNASequence> fastaMap = new LinkedHashMap<>();
            for(GenBankEntry entry : entries) {
                fastaMap.put(entry.locus.id+ " "+entry.definition,entry.origin);
            }
            return fastaMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
