import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class CP {

    private static String pairNumber = "(\\s)*-?[0-9]+(\\s)*-?[0-9]+(\\s)*-?[0-9]+";
    private static String pairName = "[a-zA-Z]+ -?[0-9]+ -?[0-9]+";
    
    private static Pattern patternNumber = Pattern.compile(pairNumber);
    private static Pattern patternName = Pattern.compile(pairName);

    private static int matches = 0;
    private static int lines = 0;
    
    public static void main(String[] args) {
	parseInput();
    }

    public static void parseInput() {
	Scanner in = new Scanner(System.in);

	while(true) {
	    if(!in.hasNextLine()) break;

	    String l = in.nextLine();
	    lines++;
	    
	    System.out.println(l);

	    Matcher matcherNumber = patternNumber.matcher(l);
	    Matcher matcherName = patternName.matcher(l);

	    if(matcherNumber.matches()) {
		parseNumberPoint(l);
	    } else if(matcherName.matches()) {
		parseNamePoint(l);
	    }
	}

	System.out.println(String.format("Lines: %d, matches: %d", lines, matches));
    }

    public static void parseNamePoint(String l) {
	System.out.println("ParseNamePoint: " + l);
	matches++;
    }

    public static void parseNumberPoint(String l) {
	System.out.println("ParseNumberPoint: " + l);
	matches++;
    }
}

/*
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
 */
