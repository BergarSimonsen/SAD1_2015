public class Pair {
    private Entity first;
    private Entity second;

    public Pair(Entity first, Entity second) {
	this.first = first;
	this.second = second;
    }

    public Pair() { }

    public Entity getFirst() { return first; }
    public void setFirst(Entity first) { this.first = first; }

    public Entity getSecond() { return second; }
    public void setSecond(Entity second) { this.second = second; }    
}
