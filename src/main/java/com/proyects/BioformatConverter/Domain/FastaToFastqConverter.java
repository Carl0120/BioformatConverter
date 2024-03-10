package com.proyects.BioformatConverter.Domain;

import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.genome.io.fastq.*;


import java.io.File;
import java.util.*;

public class FastaToFastqConverter {

    public static void convertFile(File inputFastaFile, File outputFastqFile) {
        try {

            LinkedHashMap<String, DNASequence> sequences = FastaReaderHelper.readFastaDNASequence(inputFastaFile);
            FastqIterable fastqIterable = new FastqIterable();

            for(Map.Entry<String, DNASequence> entry : sequences.entrySet()){

                String qualityValues = generateQualityValues(entry.getValue().getLength());
                Fastq fastq = convertSequenceeToFastq(entry.getKey(), entry.getValue(),qualityValues);
                fastqIterable.add(fastq);
            }
            FastqWriter fastqWriter = new SangerFastqWriter();
            fastqWriter.write(outputFastqFile,fastqIterable);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public static String generateQualityValues(int length) {
        StringBuilder qualityValues = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            qualityValues.append(random.nextInt(9) + 1);
        }
        return qualityValues.toString();
    }
    public static Fastq convertSequenceeToFastq(String description, DNASequence dnaSequence, String qualityValues) {

        String sequenceString = dnaSequence.getSequenceAsString();
        FastqBuilder builder = new FastqBuilder();
        builder.withDescription(description);
        builder.withSequence(sequenceString);
        builder.withQuality(qualityValues);
        builder.withVariant(FastqVariant.FASTQ_SANGER);

        return builder.build();
    }
}
