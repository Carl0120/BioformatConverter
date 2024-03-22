package com.proyects.BioformatConverter.FASTA.Converter;


public class ToGenBankConverter {

    public static String formatToGenBank(String description, String sequence) {
        StringBuilder genBankFormat = new StringBuilder();
        genBankFormat.append("LOCUS       ").append("PO_Geodia_  ").append("25").append("     ").append(" bp\n");
        genBankFormat.append("ACCESION" +"  "+ "PO_Geodia_\n")
                .append("ORIGIN\n");

        for (int i = 0; i < sequence.length(); i++) {
            if ((i - (i / 60))  % 60 == 0) {
                if(i==0){
                    genBankFormat.append(" ");}
                genBankFormat.append(((i + 1)-i/60) + " ");


                genBankFormat.append(sequence.charAt(i));
            } else if ((i - (i / 60)) % 10 == 0) {

                genBankFormat.append(" ");
                genBankFormat.append(sequence.charAt(i));
            }
            else {
                genBankFormat.append(sequence.charAt(i));
            }

        }

        genBankFormat.append("//\n");

        return genBankFormat.toString();
    }
}
