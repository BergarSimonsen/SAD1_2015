public class Edge {
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
