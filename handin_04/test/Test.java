import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Test {

    private static final ArrayList<Pair> l1 = new ArrayList<>();
    private static final ArrayList<Pair> l2 = new ArrayList<>();
    private static final HashSet<Pair> result = new HashSet<>();
    private static final HashSet<String> stringRes = new HashSet<>();

    private static final String title = "([a-zA-Z]|-)+--([a-zA-Z]|-)+[:] [0-9]+";
    private static final Pattern titlePattern = Pattern.compile(title);

    //    private static final String number = "\\s*-?[0-9]*";
    //    private static final Pattern numberPattern = Pattern.compile(number);    
    //	Matcher num = numberPattern.matcher(s);
    
    public static void main(String[] args) {
	try {
	    File f1 = new File(args[0]);
	    File f2 = new File(args[1]);

	    System.out.println("f1: " + args[0]);
	    System.out.println("f2: " + args[1]);

	    //	    System.out.println("reading file 1");
	    readFile(f1, l1);
	    //	    System.out.println("reading file 2");
	    readFile(f2, l2);

	    //	    System.out.printf("L1: %d, L2: %d %n", l1.size(), l2.size());

	    l1.sort((p1, p2) -> p1.compareTo(p2));
	    l2.sort((p1, p2) -> p1.compareTo(p2));

	    for(int i = 0; i < l1.size(); i++) {
		//		System.out.println("----------------------------------------------------");
		//		System.out.printf("%d == %d", l1.get(i).cost, l2.get(i).cost);
		//		System.out.printf("l1.data.length = %d, l2.data.length = %d", l1.get(i).data.length(), l2.get(i).data.length());
		//		System.out.printf("l1: %s %d %s %n", l1.get(i).name, l1.get(i).cost, l1.get(i).data);
		//		System.out.printf("l2: %s %d %s %n", l2.get(i).name, l2.get(i).cost, l2.get(i).data);
		//		System.out.println("----------------------------------------------------");		
	    }

	    for(int i = 0; i < l1.size(); i++) {
		stringRes.add(String.format("%s____%d", l1.get(i).name, l1.get(i).cost));
		stringRes.add(String.format("%s____%d", l2.get(i).name, l2.get(i).cost));		
		//	result.add(l1.get(i));
		//		result.add(l2.get(i));
	    }

	    //	    System.out.printf("l1.length: %d %nl2.length: %d %nresult.length: %d %n", l1.size(), l2.size(), stringRes.size());

	    for(String s : stringRes) {
		System.out.println(s);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }


    private static void readFile(File fin, ArrayList<Pair> list) throws IOException {
	//	System.out.println("starting readline");
	FileInputStream fis = new FileInputStream(fin);
	
	//Construct BufferedReader from InputStreamReader
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));

	String name = "";
	String data = "";
	int cost = Integer.MIN_VALUE;

	String line = null;
	while ((line = br.readLine()) != null) {
	    Matcher m = titlePattern.matcher(line);
	    //	    System.out.println("--- " + line);
	    //	    if(m.matches()) {
	    if(line.contains(":")) {
		//		System.out.println("match");
		if(name.length() > 0 && data.length() > 0 && cost > Integer.MIN_VALUE) {
		    //		    System.out.println("---------- obj exists");
		    list.add(new Pair(name, data, cost));
		    name = "";
		    data = "";
		    cost = Integer.MIN_VALUE;
		}
		
		//		System.out.println("match");
		//		System.out.println(line);
		String[] tmp = line.split(" ");
		name = tmp[0].substring(0, tmp[0].length() - 1);
		cost = Integer.parseInt(tmp[1]);
		//		System.out.println(name);
		//		System.out.println(cost);
	    } else {
		data += line;
		//		System.out.println(line);		    
	    }
	}
 
	br.close();
	fis.close();
    }
}

class Pair implements Comparable<Pair> {
    public final String name;
    public final String data;
    public final int cost;

    public Pair(String name, String data, int cost) {
	String[] tmp = name.split("--");

	String s1 = tmp[0];
	String s2 = tmp[1];

	if(s1.compareTo(s2) < 0)
	    this.name = s1 + "--" + s2;
	else
	    this.name = s2 + "--" + s1;
	
	//	this.name = name;

	String t = data.replace(" ", "");
	this.data = t;
	//	this.data = data;
	this.cost = cost;
    }

    @Override
    public int compareTo(Pair that) {
	return this.name.compareTo(that.name);
    }
}
