
import javafx.beans.property.SimpleStringProperty;
import model.Translator;
import utils.DNASequence;
import utils.ProteinSequence;

import java.util.ArrayList;

public class DNATranslator {

    private SimpleStringProperty sequence = new SimpleStringProperty();
    private SimpleStringProperty translationResult = new SimpleStringProperty();

    public void translate(ArrayList<Object[]> frameInfo) {
        StringBuilder builder = new StringBuilder();
        sequence.setValue(new DNASequence(sequence.getValue()).toString());
        for (Object[] o : frameInfo) {

            boolean rev = (boolean) o[1];
            DNASequence dnaSequence = rev ? new DNASequence(sequence.getValue()).getReverseComplement() : new DNASequence(sequence.getValue());

            int frame = (int) o[0];
            String strand = rev ? "5'3'" : "3'5'";

            builder.append(translateSequence(strand, dnaSequence, frame));
        }
        translationResult.setValue(builder.toString());
    }

    private String translateSequence(String strand, DNASequence dnaSequence, int frame) {
        ProteinSequence proteinSequence = Translator.translate(dnaSequence, frame);
        return strand + " Frame " + (frame + 1) + "\n" + proteinSequence.toString() + "\n";
    }

    public String getTranslationResult() {
        return translationResult.get();
    }

    public SimpleStringProperty translationResultProperty() {
        return translationResult;
    }

    public String getSequence() {
        return sequence.get();
    }

    public SimpleStringProperty sequenceProperty() {
        return sequence;
    }
}
