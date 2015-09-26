import java.util.Scanner;

public class NF {
    private static int n;
    private static int m;
    private static String[] nodes;
    private static Edge[] edges;
    private static Adjacencies[] gPrime;

    private static Scanner in;
    
    public static void main(String[] args) {
        parseArgs(args);
        parseInput();
        updateResidualGraph();
        int flow = 0;
        
        //Running time O(Cm), could be improved to O(Cn), will work on this.
        Path p = getNewPath();
        while(p != null) {  // O(C)
            flow = augment(flow, p); //O(n)
            updateResidualGraph(); //O(m)
            p = getNewPath();
        }
        
        //print flow? get the min cut??
    }
    
    private static void updateResidualGraph() { //O(m)
        gPrime = new Adjacencies[n];
        
        for(int i = 0 ; i < m ; i++){
            if(edges[i].flow < edges[i].capacity) {
                int residual = edges[i].capacity - edges[i].flow;
                Edge e = new Edge(edges[i].u, edges[i].v, residual, true, i);
                gPrime[e.u].leaving.add(e);
                gPrime[e.v].entering.add(e);
            }
            if(edges[i].flow > 0) {
                Edge e = new Edge(edges[i].v, edges[i].u, edges[i].flow, false, i);
                gPrime[e.u].leaving.add(e);
                gPrime[e.v].entering.add(e);
            }
        }        
    }
    
    private static int getFlow() { //O(m)
        int sum = 0;
        for(int i = 0 ; i < m ; i++) {
            sum += edges[i].flow;
        }
        return sum;
    }  
    
    private static Path getNewPath() {              //TODO DFS
        //TODO DFS
        return new Path();
    }
    
    private static int augment(int f, Path p) {
        int b = getBottleneck(f, p);
        for(Edge e : p.edges) {
            if(e.isForward)
                edges[e.gIndex].flow += b;
            else
                edges[e.gIndex].flow -= b ;
        }
        return getFlow();
    }
    
    private static int getBottleneck(int f, Path p) {
        int b = Integer.MAX_VALUE;
        for(Edge e : p.edges) {
            if(e.capacity < b) b = e.capacity;              //One of the three. I can't quite grasp
            //if(e.capacity - f < b) b = e.capacity;        //which one it's supposed to be, what f is needed for.
            //if(e.capacity - f < b) b = e.capacity - f;    //can you think about it?
        }
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