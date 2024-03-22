package com.proyects.BioformatConverter.GENBANK;

import org.biojava.nbio.core.sequence.DNASequence;

import java.util.List;

public class GenBankEntry {
    public Locus locus ;
    public String definition;
    public String accession;
    public String version;
    public String keyWords;
    public String source;
    public String reference;
    public String COMMENT;
    public List<String> features;
    public DNASequence origin;

    public GenBankEntry() {
        locus = new Locus();
        origin = new DNASequence();
    }

    public void setOrigin(DNASequence origin) {
        this.origin = origin;
    }
}