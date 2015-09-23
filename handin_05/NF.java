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
        Adjacencies[] gPrime = new Adjacencies[n];  
        
        //residual graph creation (reverted edges for the initial graph)
        for(int i = 0 ; i < m ; i++){
            gPrime[edges[i].u].entering.add(edges[i]);
            gPrime[edges[i].v].leaving.add(edges[i]);
        }        
    }
    
    private static int getFlow() {
        int sum = 0;
        for(int i = 0 ; i < m ; i++) {
            sum += edges[i].flow;
        }
        return sum;
    }
    
    private static Path getNewPath() {
        //TODO DFS
        return new Path();
    }
    
    private static int augment(int flow, Path p) {
        //TODO
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