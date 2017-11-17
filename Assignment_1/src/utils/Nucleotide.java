package utils;

import java.util.Random;

public class Nucleotide {

    public final static char[] sigma = {'A', 'T', 'C', 'G'};

    private Random rand = new Random();
    private char base;

    public Nucleotide(char c) {
        char base = Character.toUpperCase(c);
        switch (base) {
            case 'A':
            case 'T':
            case 'C':
            case 'G':
                this.base = base;
                break;
            default:
                //TODO: do exception handling
                this.base = sigma[rand.nextInt(4)];
        }
    }

    public String toString() {
        return String.valueOf(base);
    }

    public Nucleotide getComplementBase() {
        switch (base) {
            case 'A':
                return new Nucleotide('T');
            case 'T':
                return new Nucleotide('A');
            case 'C':
                return new Nucleotide('G');
            default:
                return new Nucleotide('C');
        }
    }

}
