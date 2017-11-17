package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class FASTA_Reader {

    public static HashMap<String, String> run(File f) {

        HashMap<String, String> sequences = new HashMap<String, String>();
        try {

            BufferedReader buf = new BufferedReader(new FileReader(f));
            StringBuilder builder = new StringBuilder();
            String l, id = null;
            while ((l = buf.readLine()) != null) {
                if (l.startsWith(">")) {
                    if (id != null)
                        sequences.put(id, builder.toString());
                    id = l.substring(1);
                    builder = new StringBuilder();
                } else
                    builder.append(l);
            }
            if (id != null)
                sequences.put(id, builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sequences;

    }

}
