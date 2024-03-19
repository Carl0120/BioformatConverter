package com.proyects.BioformatConverter.FASTA.Converter;

import com.proyects.BioformatConverter.Entity.FastqIterable;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.genome.io.fastq.*;
import java.util.*;

public class FastaToFastqConverter {

    public static FastqIterable convertToFastq(LinkedHashMap<String, DNASequence> sequences) {
        try {
            FastqIterable fastqIterable = new FastqIterable();

            for(Map.Entry<String, DNASequence> entry : sequences.entrySet()){

                String qualityValues = generateQualityValues(entry.getValue().getLength());
                Fastq fastq = convertSequenceeToFastq(entry.getKey(), entry.getValue(),qualityValues);
                fastqIterable.add(fastq);
            }
            return fastqIterable;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String generateQualityValues(int length) {
        StringBuilder qualityValues = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int b =  (random.nextInt(94) + 33);
            qualityValues.append((char) b);
        }
        return qualityValues.toString();
    }
    private static Fastq convertSequenceeToFastq(String description, DNASequence dnaSequence, String qualityValues) {

        String sequenceString = dnaSequence.getSequenceAsString();
        FastqBuilder builder = new FastqBuilder();
        builder.withDescription(description);
        builder.withSequence(sequenceString);
        builder.withQuality(qualityValues);
        builder.withVariant(FastqVariant.FASTQ_SANGER);

        return builder.build();
    }
}
