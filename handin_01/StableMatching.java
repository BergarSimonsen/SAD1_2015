import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class StableMatching {

    private static StableMatching sm;

    private static boolean doDebug = false;
    
    private int n;
    private Entity[] a;
    private Entity[] b;
    private Pair[] match;

    static int mIndex = 0;
    static int fIndex = 0;

    static ArrayList<Entity> freeMen = new ArrayList<Entity>();
    static ArrayList<Pair> matches = new ArrayList<Pair>();
    static Entity[] wife;
    static Entity[] husband;

    public static void parseInput() {    
	Scanner in = new Scanner(System.in);
	
	String p1 = "\\d* .*";
	String p2 = "\\d*(:) .*";
	Pattern r = Pattern.compile(p1);
	Pattern r2 = Pattern.compile(p2);
	
	// p1 and r are regex patterns for finding the entities, i.e. 1 Sheldon
	// p2 and r2 are regex patterns for finding the preferences, i.e. 1: 2 4 6 8

	while(true) {
	    if(!in.hasNextLine()) break;
	    String l = in.nextLine();
	    if(l == null) continue;
	    Matcher m = r.matcher(l);
	    Matcher m1 = r2.matcher(l);
	    if(l.startsWith("#")) continue;
	    else if(l.startsWith("n")) {
		sm.setN(sm.parseN(l));
		sm.setA(new Entity[sm.getN()]);
		sm.setB(new Entity[sm.getN()]);
		sm.setMatch(new Pair[sm.getN()]);
	    } else if(m.matches()) {
		parseEntities(l);
	    } else if(m1.matches()) {
		parsePreferenceLists(l);
	    }
	}

	for(Entity e : sm.getA())
	    freeMen.add(e);

	int step = 0;

	while(freeMen.size() > 0) {
	    Entity m = freeMen.get(freeMen.size() - 1);
	    int curPref = m.getCurrentPref();

	    Entity w = null;

	    //	    System.out.println("------------ " + curPref);
	    // Check if woman is matched
	    for(Pair p : matches) {
		if(p.getSecond().getIndex() == m.getPrefs()[curPref]) {
		    // is matched!
		    w = p.getSecond();

		    // check if m is better match
		    int mIndex = m.getIndex();
		    int curMatchIndex = -1;
		    int mMatchIndex = -1;

		    for(int j = 0; j < w.getPrefs().length; j++) {
			if(w.getPrefs()[j] == mIndex)
			    mMatchIndex = j;
			if(w.getPrefs()[j] == p.getFirst().getIndex())
			    curMatchIndex = j;

			if(mMatchIndex > -1 && curMatchIndex > -1) break;
		    }

		    //		    System.out.println("curMatch: " + curMatchIndex + " mMatch: " + mMatchIndex);
		    
		    if(curMatchIndex < mMatchIndex) {
			//			System.out.println("existing match!");
			break;
		    } else {
			//			System.out.println("replacing match, " + freeMen.size());
			
			Entity tmp = p.getFirst();
			p.setFirst(m);
			freeMen.remove(freeMen.size() - 1);
			freeMen.add(tmp);
		    }
		}
	    }

	    // w is not matched
	    if(w == null) {
		//		System.out.println("w is not matched!");
		for(int i = 0; i < sm.getB().length; i++) {
		    if(sm.getB()[i].getIndex() == m.getPrefs()[curPref]) {
			w = sm.getB()[i];
			//			System.out.println("Found!");
			break;
		    }
		}

		if(w != null) {
		    matches.add(new Pair(m, w));
		    freeMen.remove(freeMen.size() - 1);
		}
	    } 

	    //	    System.out.println(String.format("Step: %d, matches.size: %d, freeMen.size() %d", step, matches.size(), freeMen.size()));
	    //	    for(Pair p : matches) {
	    //		System.out.println(p.getFirst().getName() + " -- " + p.getSecond().getName());
	    //	    }
	    step++;

	}
	for(Pair p : matches)
	    System.out.println(p.getFirst().getName() + " -- " + p.getSecond().getName());
    }

    public static void parsePreferenceLists(String l) {    
	String[] s = l.split(" ");
	int index = Integer.parseInt(s[0].substring(0, s[0].length()-1));
	int[] tmp = new int[sm.getN()];
	
	for(int i = 1; i < s.length; i++) tmp[i - 1] = Integer.parseInt(s[i]);
	
	if(index % 2 == 0) {
	    for(int i = 0; i < sm.getN(); i++) {
		if(sm.getB()[i].getIndex() == index) {
		    sm.getB()[i].setPrefs(tmp);
		    break;
		}
	    }
	} else {
	    for(int i = 0; i < sm.getN(); i++) 
		if(sm.getA()[i].getIndex() == index) {
		    sm.getA()[i].setPrefs(tmp);
		    break;
		}
	}
    }

    public static void parseEntities(String l) {    
	String[] s = l.split(" ");

	int num = Integer.parseInt(s[0]);
	if(num % 2 == 0) {
	    sm.getB()[fIndex] = new Entity(num, s[1], sm.getN());
	    fIndex++;
	} else {
	    sm.getA()[mIndex] = new Entity(num, s[1], sm.getN());		    
	    mIndex++;
	}
    }
    
    public static void main(String[] args) {
	sm = new StableMatching();
	parseInput();
	
    }
    
    public int parseN(String l) { return Integer.parseInt(l.substring(2)); }

    public void setN(int n) { this.n = n; }
    public int getN() { return this.n; }

    public Entity[] getA() { return this.a; }
    public void setA(Entity[] arr) { this.a = arr; }
    
    public Entity[] getB() { return this.b; }
    public void setB(Entity[] arr) { this.b = arr; }

    public Pair[] getMatch() { return this.match; }
    public void setMatch(Pair[] match) { this.match = match; }

    public void debug() {
	if(doDebug) {
	    System.out.println("----------------------------------");
	    System.out.println("n: " + n);
	    System.out.println("a.length: " + a.length);
	    System.out.println("b.length: " + b.length);
	    System.out.println("a:");
	    for(int i = 0; i < a.length; i++)
		System.out.println(a[i].toString());
	    System.out.println("b:");
	    for(int i = 0; i < b.length; i++)
		System.out.println(b[i].toString());
	    System.out.println("----------------------------------");
	}
    }
}
