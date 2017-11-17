package io;

import model.Translator;
import utils.DNASequence;
import utils.ProteinSequence;

import java.io.File;
import java.util.HashMap;

public class CommandLine {

    public static void run(File f) {

        HashMap<String, String> sequences = FASTA_Reader.run(f);
        for (String id : sequences.keySet()) {

            System.out.println("> " + id);
            String s = sequences.get(id);
            DNASequence dnaSequence = new DNASequence(s);
            DNASequence revDnaSequence = dnaSequence.getReverseComplement();

            System.out.println(revDnaSequence.toString());

            int lineWidth = 100;
            translateSequence("5'3'", dnaSequence, 0, lineWidth);
            translateSequence("5'3'", dnaSequence, 1, lineWidth);
            translateSequence("5'3'", dnaSequence, 2, lineWidth);
            translateSequence("3'5'", revDnaSequence, 0, lineWidth);
            translateSequence("3'5'", revDnaSequence, 1, lineWidth);
            translateSequence("3'5'", revDnaSequence, 2, lineWidth);

        }

    }

    private static void translateSequence(String strand, DNASequence dnaSequence, int frame, int lineWidth) {
        ProteinSequence proteinSequence = Translator.translate(dnaSequence, frame);
        System.out.println(strand + " Frame " + (frame + 1));
        String s = proteinSequence.toString();
        for (int i = 0; i < s.length(); i += lineWidth) {
            int j = i + lineWidth < s.length() ? i + lineWidth : s.length();
            System.out.println(s.substring(i, j));
        }
    }

}
