import java.io.*;
public class GraphPrinter {
    private static final String file = "data/graph.dot";
    public static boolean doGenerate = false;


    private static boolean writeFile(String content) {
	File f = new File(file);

	try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))) {
	    writer.write(content);
	    return true;
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
    }
    
    public static void drawGraph(Edge[] edges, String[] nodes) {
	if(doGenerate) {
	    String s = "digraph G {\n";

	    for(int i = 0; i < edges.length; i++) {
		int c = edges[i].capacity;
		//		int curCap = c == -1 ? -1 : Integer.parseInt(nodes[c]);
		s += "\"" + nodes[edges[i].u] + "\" -> \"" + nodes[edges[i].v] + "\" [label=" + c + "]\n";
	    }

	    s += "}";

	    boolean success = writeFile(s);

	    if(success) {
		generateGraph();
	    }
	}
    }

    private static void generateGraph() {
	try {
	    Process p;
	    // dot -Tps filename.dot -o outfile.ps
	    //	    dot -Tpng comm_diagram.dot -o kommunikations_diagram.png
	    p = Runtime.getRuntime().exec("dot -Tpng " + file + " -o data/graph.png");
	    p.waitFor();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
