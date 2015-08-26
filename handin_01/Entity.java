public class Entity {
    private int index;
    private String name;
    private int[] prefs;
    private int[] inversePrefs;
    private int currentPref;
    private int n;

    public Entity(int index, String name, int[] prefs, int n) {
	this.index = index;
	this.name = name;
	this.prefs = prefs;
	this.currentPref = 0;
	this.n = n;
    }

    public Entity(int index, String name, int n) {
	this.index = index;
	this.name = name;
	this.currentPref = 0;
	this.n = n;
    }

    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int[] getPrefs() { return prefs; }
    public void setPrefs(int[] prefs) {
	this.prefs = prefs;
	this.inversePrefs = new int[prefs.length];
	for(int i = 1; i <= prefs.length; i++)
	    inversePrefs[i-1] = i;
    }

    public int[] getInversePrefs() { return inversePrefs; }

    public int getCurrentPref() {
	int x = currentPref;
	if(currentPref < n-1)
	    currentPref++;
	return x;
    }

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
