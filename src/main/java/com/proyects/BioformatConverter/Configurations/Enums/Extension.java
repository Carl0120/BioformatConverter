package com.proyects.BioformatConverter.Configurations.Enums;

public enum Extension {
    FASTALong(".fasta"),
    FASTAShort(".fa"),
    FASTQLong(".fastq"),
    FASTQShort(".fq"),
    PHYLIPShort(".phy");


    private final String extension;

    Extension(String extension) {
        this.extension = extension;
    }

    public String get() {
        return extension;
    }
}
