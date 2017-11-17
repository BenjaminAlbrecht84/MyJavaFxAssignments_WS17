package utils;

import java.util.ArrayList;

public class ProteinSequence extends Sequence {

    public ProteinSequence(ArrayList<AminoAcid> aminoacids) {
        type = Type.PROTEIN;
        sequence = aminoacids;
    }

    public ProteinSequence(String s) {
        type = Type.PROTEIN;
        sequence = new ArrayList<AminoAcid>();
        for (int i = 0; i < s.length(); i++)
            sequence.add(new AminoAcid(s.charAt(i)));
    }

    public String toString(){
        return super.toString();
    }

}
