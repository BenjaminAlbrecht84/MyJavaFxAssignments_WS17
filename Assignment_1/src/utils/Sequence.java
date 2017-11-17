package utils;

import java.util.ArrayList;

public abstract class Sequence {

    public enum Type {DNA, PROTEIN}

    protected Type type;
    protected ArrayList sequence;

    public ArrayList getSequence() {
        return sequence;
    }

    public Type getType() {
        return type;
    }

    public int length() {
        return sequence.size();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(sequence.size());
        for (Object o : sequence)
            builder.append(o.toString());
        return builder.toString();
    }


}
