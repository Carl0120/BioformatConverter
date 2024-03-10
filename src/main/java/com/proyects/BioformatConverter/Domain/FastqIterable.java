package com.proyects.BioformatConverter.Domain;

import org.biojava.nbio.genome.io.fastq.Fastq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FastqIterable implements Iterable<Fastq>{

    private final List<Fastq> fastqList;
    public FastqIterable() {
        fastqList = new ArrayList<>();
    }
    public void add(Fastq fastq) {
        fastqList.add(fastq);
    }

    @Override
    public Iterator<Fastq> iterator() {

        return fastqList.iterator();
    }
}
