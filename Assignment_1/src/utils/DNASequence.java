package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DNASequence extends Sequence {

    public DNASequence(ArrayList<Nucleotide> nucleotides) {
        type = Type.DNA;
        sequence = nucleotides;
    }

    public DNASequence(List<Nucleotide> nucleotides) {
        type = Type.DNA;
        sequence = new ArrayList(Arrays.asList(nucleotides));
    }

    public DNASequence(String s) {
        type = Type.DNA;
        sequence = new ArrayList<Nucleotide>();
        for (int i = 0; i < s.length(); i++)
            sequence.add(new Nucleotide(s.charAt(i)));
    }

    public DNASequence getReverseComplement() {
        ArrayList<Nucleotide> complementBases = new ArrayList<Nucleotide>();
        for (Object o : sequence)
            complementBases.add(0, ((Nucleotide) o).getComplementBase());
        return new DNASequence(complementBases);
    }

    public ArrayList<DNASequence> getTriplets(int startPos) {
        ArrayList<DNASequence> triplets = new ArrayList<DNASequence>();
        ArrayList<Nucleotide> nucleotides = new ArrayList<Nucleotide>();
        for (int i = startPos; i < length(); i++) {
            if (nucleotides.size() == 3) {
                triplets.add(new DNASequence(nucleotides));
                nucleotides = new ArrayList<Nucleotide>();
            }
            nucleotides.add((Nucleotide) sequence.get(i));
        }
        if (nucleotides.size() == 3)
            triplets.add(new DNASequence(nucleotides));
        return triplets;
    }

}
