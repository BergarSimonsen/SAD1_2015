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

    private static  Match[][] Memoizer;
	
    public static void main(String[] args) {
	parseArgs(args);
	parseInput();
	parseCost();
	entArr = entities.toArray(new Entity[entities.size()]);

	for(int i = 0; i<entArr.length; i++){
	    for(int j = i+1; j<entArr.length; j++){
		compare(entArr[i],entArr[j]);
	    }
	}

	for(int i = 0; i < Memoizer.length; i++) {
	    for(int j = 0; j < Memoizer[i].length; j++) {
		Logger.log(Memoizer[i][j].toString());
	    }
	}
		
	//Logger.log(entArr);
    }

    private static void compare(Entity entity1, Entity entity2){
	Memoizer = new Match[entity1.data.length+1][entity2.data.length+1];
		
	Match match = maximumCost(entity1.data, entity2.data);
	int cost = match.cost;
	String data1 = match.data1;
	String data2 = match.data2;

	/*	Logger.log(entity1.name + " -- " + entity2.name + " " + cost);

	Logger.log("Data1: " + data1);
	Logger.log("Data2: " + data2); */
    }
	
    private static Match maximumCost(String[] pdata1, String[] pdata2) {
	if (Memoizer[pdata1.length][pdata2.length] != null)
	    return Memoizer[pdata1.length][pdata2.length];
	else {
	    Match bestMatch;
	    // =Integer.MIN_VALUE;
	    if (pdata1.length == 0 && pdata2.length == 0) {

		bestMatch = new Match(0, new String(), new String());

	    } else if (pdata1.length == 0) {

		char letter2 = pdata2[0].toCharArray()[0];
		int letter2Index = findIndex(letter2);
		int _up = cost[23][letter2Index];
		String[] _pdata2 = Arrays.copyOfRange(pdata2, 1, pdata2.length);

		Match case2 = maximumCost(pdata1, _pdata2);
		int case2Cost = _up + case2.cost;
		String data1 = "-" + case2.data1;
		String data2 = letter2 + case2.data2;

		bestMatch = new Match(case2Cost, data1,data2);
				
		// matchStrings('-', letter2);

	    } else if (pdata2.length == 0) {

		char letter1 = pdata1[0].toCharArray()[0];
		int letter1Index = findIndex(letter1);
		int _down = cost[letter1Index][23];
		String[] _pdata1 = Arrays.copyOfRange(pdata1, 1,
						      pdata1.length);

		Match case3 = maximumCost(_pdata1, pdata2);

		int case3Cost = _down + case3.cost;
		String data1 = letter1 + case3.data1;
		String data2 = "-" + case3.data2;

		bestMatch = new Match(case3Cost, data1, data2);
				

	    } else {
		char letter1 = pdata1[0].toCharArray()[0];
		int letter1Index = findIndex(letter1);
		char letter2 =pdata2[0].toCharArray()[0];
		int letter2Index = findIndex(letter2);

		int match = cost[letter1Index][letter2Index];
		int _up = cost[23][letter2Index];
		int _down = cost[letter1Index][23];

		String[] _pdata1 = Arrays.copyOfRange(pdata1, 1,
						      pdata1.length);
		String[] _pdata2 = Arrays.copyOfRange(pdata2, 1,
						      pdata2.length);

		Match case1 = maximumCost(_pdata1, _pdata2);
		Match case2 = maximumCost(pdata1,_pdata2);
		Match case3 = maximumCost(_pdata1, pdata2);

		int case1Cost = match + case1.cost;
		int case2Cost = _up + case2.cost;
		int case3Cost = _down + case3.cost;

		int maxCost;
		String data1;
		String data2;

		maxCost = case1Cost;
		data1= letter1 + case1.data1;
		data2 = letter2 + case1.data2;

		if (maxCost < case2Cost) {
		    maxCost = case2Cost;
		    data1 = "-"+case2.data1;
		    data2 = letter2 + case2.data2;
		}
		if (maxCost < case3Cost) {
		    maxCost = case3Cost;
		    data1 = letter1 +case3.data1;
		    data2 = "-" + case3.data2;
		}

		bestMatch = new Match(maxCost, data1, data2);

				
	    }

	    Memoizer[pdata1.length][pdata2.length] = bestMatch;
	    return bestMatch;
	}
    }
	
    private static int findIndex(char letter) {
	for (int i = 0; i < costLabel.length; i++) {
	    if (costLabel[i] == String.valueOf(letter))
		return i;
	}
	return 23;
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

class Match {
	
    public int cost;
    public String data1;
    public String data2;
	
    public Match(int cost, String data1, String data2){
		
	this.cost = cost;
	this.data1 = data1;
	this.data2 = data2;
    }

    @Override
    public String toString() {
	return String.format("cost: %d, data1: %s, data2: %s", cost, data1, data2);
    }
}
