import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

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
        
        //Running time O(Cm), assuming m > n. Could be improved to O(Cn), will work on this.
        List<Edge> p = getNewPath();
        int c = 0;
        while(p != null) {  // O(C)
            System.out.println("Iteration: " + c + ", flow: " + flow);
            flow = augment(flow, p); //O(n)
            updateResidualGraph(); //O(m)  >> could be eliminated
            p = getNewPath();
            c++;
        }
        
        System.out.println("Flow = " + flow);
    }
    
    private static void updateResidualGraph() { //O(m)
        gPrime = new Adjacencies[n];
        for(int i = 0 ; i < n ; i++)
            gPrime[i] = new Adjacencies();
        
        for(int i = 0 ; i < m ; i++){
            if(edges[i].flow < edges[i].capacity || edges[i].capacity == -1) {
                int residual;
                if( edges[i].capacity == -1)        //infinite capacity
                    residual = Integer.MAX_VALUE;
                else residual = edges[i].capacity - edges[i].flow;
                
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
    
    private static List<Edge> getNewPath() {  //O(n + m) = O(m) for m > n
        boolean discovered[] = new boolean[n]; 
        List<Edge> path = new ArrayList<>();
        DFS(0, n-1, path, discovered);          //O(n + m) = O(m) for m > n
        
        List<Edge> reversed = new ArrayList<>(); //O(n)
        for(int i = path.size() - 1 ; i >=0 ; i--)
            reversed.add(path.get(i));
        
        return reversed;
    }
    
    private static int DFS(int v, int end, List<Edge> p, boolean[] discovered) { //O(n + m) = O(m) for m > n
        discovered[v] = true;
        for(Edge e : gPrime[v].leaving) {
            if(!discovered[e.v]) {
                int curEnd = DFS(e.v, end, p, discovered);
                if(e.v == curEnd) {
                    p.add(e);
                    return v;                    
                }
            }                
        }
        return end;
    }
    
    private static int augment(int f, List<Edge> p) {
        int b = getBottleneck(f, p);
        for(Edge e : p) {
            if(e.isForward)
                edges[e.gIndex].flow += b;
            else
                edges[e.gIndex].flow -= b;
        }
        return getFlow();
    }
    
    private static int getBottleneck(int f, List<Edge> p) {
        int b = Integer.MAX_VALUE;
        for(Edge e : p) {
            if(e.capacity < b) b = e.capacity;              //One of the three. I can't quite grasp
            //if(e.capacity - f < b) b = e.capacity;        //which one it's supposed to be, what f is needed for.
            //if(e.capacity - f < b) b = e.capacity - f;    //can you think about it?
        }
        return b;
    }
    
    public static void parseInput() {
	// parse n
	in = new Scanner(System.in);
    //try {
    //    in = new Scanner(new FileReader("input.txt"));
    //} catch (FileNotFoundException ex) {
    //    java.util.logging.Logger.getLogger(NF.class.getName()).log(Level.SEVERE, null, ex);
    //}
        
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
