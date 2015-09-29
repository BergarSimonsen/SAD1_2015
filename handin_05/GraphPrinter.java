import java.io.*;
public class GraphPrinter {
    private static final String file = "data/graph.dot";
    private static final String filePng = "data/graph.png";
    private static final String resGraph = "data/residualGraph.dot";
    private static final String resGraphPng = "data/residualGraph.png";
    public static boolean doGenerate = false;


    private static boolean writeFile(String filename, String content) {
	File f = new File(filename);

	try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf-8"))) {
	    writer.write(content);
	    return true;
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public static void drawResidualGraph(Adjacencies[] adj, String[] nodes, String filename) {
	if(doGenerate) {
	    String dotFile = filename + ".dot";
	    String pngFile = filename + ".png";
	    
	    
	    String s = "digraph RG {\n";
	    for(int i = 0; i < adj.length; i++) {
		Adjacencies a = adj[i];
		/*		for(int j = 0; j < a.entering.size(); j++) {
		    s += "\"" + nodes[a.entering.get(j).v] + "\" -> \"" + nodes[a.entering.get(j).u] + "\" [label=" + a.entering.get(j).capacity + "]\n";
		    } */

		for(int j = 0; j < a.leaving.size(); j++) {
		    s += "\"" + nodes[a.leaving.get(j).u] + "\" -> \"" + nodes[a.leaving.get(j).v] + "\" [label=" + a.leaving.get(j).capacity + "color=red]\n";
		}
	    }
	    s += "}";

	    boolean success = writeFile(dotFile, s);

	    System.out.println("Success: " + success);

	    if(success)
		generateGraph(dotFile, pngFile);
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

	    boolean success = writeFile(file, s);

	    if(success) {
		generateGraph(file, filePng);
	    }
	}
    }

    private static void generateGraph(String inputFile, String outputFile) {
	try {
	    Process p;
	    // dot -Tps filename.dot -o outfile.ps
	    //	    dot -Tpng comm_diagram.dot -o kommunikations_diagram.png
	    p = Runtime.getRuntime().exec("dot -Tpng " + inputFile + " -o " + outputFile);
	    p.waitFor();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
