import io.CommandLine;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        File fastaFile = new File(args[0]);
        CommandLine.run(fastaFile);
        
    }

}
