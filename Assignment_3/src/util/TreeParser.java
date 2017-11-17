package util;

import java.io.*;
import java.util.ArrayList;

public class TreeParser {

    public static ArrayList<Object[]> parseNodeInfo(File f) throws IOException {

        ArrayList<Object[]> nodeInfo = new ArrayList<Object[]>();
        BufferedReader buf = new BufferedReader(new FileReader(f));
        String l;
        while ((l = buf.readLine()) != null) {
            if (l.startsWith("Edge"))
                break;
            if (!l.startsWith("Node")) {
                String[] content = l.split("\\s+");
                String id = content[0];
                int x = Integer.parseInt(content[1]);
                int y = Integer.parseInt(content[2]);
                String label = content.length > 3 ? content[3] : null;
                Object[] info = {id, x, y, label};
                nodeInfo.add(info);
            }
        }

        return nodeInfo;

    }

    public static ArrayList<String[]> parseEdgeInfo(File f) throws IOException {

        ArrayList<String[]> edgeInfo = new ArrayList<String[]>();
        BufferedReader buf = new BufferedReader(new FileReader(f));
        String l;
        boolean parseEdgeInfo = false;
        while ((l = buf.readLine()) != null) {
            if (parseEdgeInfo)
                edgeInfo.add(l.split("\\s+"));

            else if (l.startsWith("Edge"))
                parseEdgeInfo = true;
        }

        return edgeInfo;
    }


}
