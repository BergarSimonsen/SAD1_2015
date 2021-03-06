import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DP {
    private static ArrayList<Entity> entities;
    private static Entity[] entArr;
    private static int[][] cost;
    private static String[] costLabel = {"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V", "B", "Z", "X", "*"};
    private static Scanner in;

    private static final String number = "\\s*-?[0-9]*";
    private static final Pattern numberPattern = Pattern.compile(number);

    private static final String COST_FILE = "data/BLOSUM62.txt";
    private static final int COST_DIMENSION = 24;

    public static void main(String[] args) {
	parseArgs(args);
	parseInput();
	parseCost();
	entArr = entities.toArray(new Entity[entities.size()]);

	//Logger.log(entArr);
    }

    public static void parseArgs(String[] args) {
	if(args.length > 0) {
	    for(String s : args) {
		if(s.equals("-v")) Logger.debug = true;
	    }
	}
    }

    private static void parseCost() {
	try {
	    cost = new int[COST_DIMENSION][COST_DIMENSION];

	    int l = 0;
	    int k = 0;
	    
	    BufferedReader br = new BufferedReader(new FileReader(new File(COST_FILE)));
	    String line = br.readLine();
	    while(line != null) {
		if(!line.startsWith("#")) {
		    String[] tmp = line.split(" ");
		    if(l++ > 0) {
			int j = 0;			
			for(int i = 0; i < tmp.length; i++) {
			    if(tmp[i] == null || tmp[i].length() == 0 || tmp[i] == " ")
				continue;
			    else if(isNumber(tmp[i])) {
				if(j < COST_DIMENSION && k < COST_DIMENSION) {
				    String s = tmp[i].trim();
				    cost[k][j++] = Integer.parseInt(s);
				}
			    }
			}
			k++;
		    }
		}
		
		line = br.readLine();
	    }
	    br.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private static boolean isNumber(String s) {
	Matcher num = numberPattern.matcher(s);
	return num.matches();
    }

    private static void parseInput() {
	entities = new ArrayList<Entity>();
	in = new Scanner(System.in);

	while(in.hasNextLine()) {
	    String l = in.nextLine();
	    if(l.startsWith(">")) {
		parseEntity(l);
	    }
	}
    }

    private static void parseEntity(String l) {
	String[] name = l.split(" ");
	ArrayList<String> data = new ArrayList<String>();
	l = in.nextLine();
	while(!l.startsWith(">")) {
	    data.add(l);
	    if(in.hasNextLine()) {
		l = in.nextLine();
		entities.add(Entity.createEntity(name, data));
	    } else {
		entities.add(Entity.createEntity(name, data));		
		break;
	    }
	}
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

    public static void log(Entity[] arr) {
	for(int i = 0; i < arr.length; i++)
	    log(String.format("[%d]: %s", i, arr[i].toString()));
    }
}

class Entity {
    public String name;
    public String[] data;

    public Entity(String name, int dataElements) {
	this.name = name.substring(1);
	this.data = new String[dataElements];
    }

    public Entity(String name, ArrayList<String> dataList) {
	this.name = name.substring(1);
	this.data = new String[dataList.size()];
	for(int i = 0; i < dataList.size(); i++)
	    this.data[i] = dataList.get(i);
    }

    @Override
    public String toString() {
	String dataString = "";
	for(String s : data)
	    dataString += s + " ";
	return String.format("name: %s, data: %s", name, dataString);
    }

    public static Entity createEntity(String[] name, ArrayList<String> data) {
	String n = "";
	for(String s : name)
	    n += s + " ";
	n = n.substring(0, n.length() - 1); // remove last whitespace
	return new Entity(n, data);
    }    
}
