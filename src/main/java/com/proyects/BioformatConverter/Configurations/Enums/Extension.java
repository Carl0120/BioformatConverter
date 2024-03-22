package com.proyects.BioformatConverter.Configurations.Enums;

public enum Extension {
    FASTALong(".fasta"),
    FASTAShort(".fa"),
    FASTQLong(".fastq"),
    FASTQShort(".fq"),
    GENBANKShort(".gb"),
    PHYLIPShort(".phy");


    private final String extension;

    Extension(String extension) {
        this.extension = extension;
    }

    public String get() {
        return extension;
    }
}
