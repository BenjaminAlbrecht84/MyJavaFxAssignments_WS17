package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AminoAcid {

    public final static Character[] sigma = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y',
            'V', 'B', 'Z', 'J', 'X', '*'};

    private char aa;
    private Random rand = new Random();

    public AminoAcid(char c) {
        char aa = Character.toUpperCase(c);
        ArrayList<Character> sigmaList = new ArrayList<Character>(Arrays.asList(sigma));
        if (sigmaList.contains(aa))
            this.aa = aa;
        else {
            //TODO: do exception handling
            System.out.println(rand.nextInt(sigmaList.size()));
            this.aa = sigmaList.get(rand.nextInt(sigmaList.size()));
        }
    }

    public String toString() {
        return String.valueOf(aa);
    }

}
