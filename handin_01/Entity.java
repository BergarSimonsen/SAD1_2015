public class Entity {
    private int index;
    private String name;
    private int[] prefs;

    public Entity(int index, String name, int[] prefs) {
	this.index = index;
	this.name = name;
	this.prefs = prefs;
    }

    public Entity(int index, String name) {
	this.index = index;
	this.name = name;
    }

    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int[] getPrefs() { return prefs; }
    public void setPrefs(int[] prefs) { this.prefs = prefs; }

    public String toString() {
	String p = "";
	if(prefs != null) {
	    p += "[";
	    for(int i : prefs) p += i + ", ";
	    p = p.substring(0, p.length() - 1);
	    p += "]";
	} else p = "[]";
	return String.format("Index: %d, name: %s, Prefs: %s", index, name, p);
    }
}
