import java.util.List;
import java.util.Scanner;

public class NF {
    private static int n;
    private static int m;
    private static String[] nodes;
    private static Edge[] edges;

    private static Scanner in;
    
    public static void main(String[] args) {
		parseArgs(args);
        parseInput();
        //Gf kept as an adjacency list
         List<List<Edge>>gPrime;  
        
        
    }
    
    private static int augment() {
        
        return 0;
    }
    
    public static void parseInput() {
	// parse n
	in = new Scanner(System.in);
	String l = in.nextLine();
	if(l == null) die("error parsing n");
	
	n = Integer.parseInt(l);
	nodes = new String[n];

	// parse n nodes
	for(int i = 0; i < n; i++) {
	    l = in.nextLine();
	    if(l != null)
		nodes[i] = l;
	}
	
	// parse m
	l = in.nextLine();
	m = Integer.parseInt(l);
	edges = new Edge[m];
	
	// parse m edges
	for(int i = 0; i < m; i++) {
	    l = in.nextLine();
	    if(l != null)
		edges[i] = new Edge(l.split(" "));
	}
	in.close();
    }

    public static void parseArgs(String[] args) {
	if(args.length > 0) {
	    for(String s : args) {
		if(s.equals("-v")) Logger.debug = true;
	    }
	}
    }

    private static void die(String msg) {
	System.err.println(msg);
	System.exit(1);
    }
}

class Edge {
    public final int u;
    public final int v;
    public final int capacity;
    public int flow;
    
    public Edge(int u, int v, int capacity){
        this.u = u;
        this.v = v;
        this.capacity = capacity;
        this.flow = 0;
    }
    
     public Edge(String[] data) {
	this.u = Integer.parseInt(data[0]);
	this.v = Integer.parseInt(data[1]);
	this.capacity = Integer.parseInt(data[2]);
        this.flow = 0;
    }

    @Override
    public String toString() {
	return String.format("%d -- %d (%d)", u, v, capacity);
    }     
}

class Logger {
    public static boolean debug = false;
    
    public static void log(String msg) {
	if(debug) System.out.println(msg);
    }

    public static void log(int msg) {
	log(String.valueOf(msg));
    }

    public static void log(String[] arr) {
	for(int i = 0; i < arr.length; i++)
	    log(String.format("[%d]: %s%n", i, arr[i]));
    }

    public static void log(Edge[] arr) {
	for(int i = 0; i < arr.length; i++)
	    log(String.format("[%d]: %s%n", i, arr[i].toString()));
    }    
}