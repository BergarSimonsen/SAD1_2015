import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CP {
    /*
      Regex explanation:
      
      pairNumber matches numbers where the first element is a number: 270 180 125
      pairName matches numbers where the first element is a name: romeo 0 0
      pairScientific matches numbers in scientific notation: 12 1.09100e+03 9.58300e+02
    */
    
    private static final String pairNumber = "(\\s)*-?[0-9]+(\\s)*-?[0-9]+(\\s)*-?[0-9]+";
    private static final String pairName = "[a-zA-Z]+ -?[0-9]+ -?[0-9]+";
    private static final String dimension = "DIMENSION: [0-9]+";
    private static final String pairScientific = "(\\s)*-?[0-9]+(\\s)*-?[0-9]+-?[0-9]*[.][0-9]*[e][+][0-9]+(\\s)*-?[0-9]*[.][0-9]*[e][+][0-9]+";

    private static final Pattern patternNumber = Pattern.compile(pairNumber);
    private static final Pattern patternName = Pattern.compile(pairName);
    private static final Pattern patternDimension = Pattern.compile(dimension);
    private static final Pattern patternScientific = Pattern.compile(pairScientific);
    
    private static int n = 0;
    private static Point P[];
    private static int curIndex = 0;
    
    
    public static void main(String[] args) {
        parseInput();

	if(P == null) die();

	printPointArray(P);
        
	/*        Point Px[] = new Point[n];        
        Point Py[] = new Point[n];        
        System.arraycopy(P, 0, Px, 0, P.length);
        System.arraycopy(P, 0, Py, 0, P.length);
        Arrays.sort(Px, (a, b) -> (int) (a.getX() - b.getY()));
        Arrays.sort(Px, (a, b) -> (int) (a.getX() - b.getY())); */
        
	//        Point result = closestPairRec(Px, Py);
    }
    
    
    private static Point closestPairRec(Point[] px, Point[] py) {
        return null;
    }
    
    public static void parseInput() {
	Scanner in = new Scanner(System.in);

	while(true) {
	    if(!in.hasNextLine()) break;

	    String l = in.nextLine();
	    
	    Matcher matcherNumber = patternNumber.matcher(l);
	    Matcher matcherName = patternName.matcher(l);
	    Matcher matcherScientific = patternScientific.matcher(l);
	    Matcher matcherDimension = patternDimension.matcher(l);

	    if(matcherDimension.matches()) {
		parseDimension(l);
	    } else if(matcherNumber.matches() || matcherName.matches() || matcherScientific.matches()) {
		if(P != null)
		    parsePoint(l);
		else
		    die();
	    } else {
		System.out.println("no match");
	    }
	}
    }

    public static void parseDimension(String l) {
	String[] tmp = l.split(" ");
	n = Integer.parseInt(tmp[1]);
	P = new Point[n];
    }
    
    public static void parsePoint(String l) {
	String[] tmp = l.split(" ");
	ArrayList<String> list = new ArrayList<String>();

	for(String s : tmp) {
	    if(s != null && s.length() > 0)
		list.add(s);
	}

	//	Point p = new Point(Double.parseDouble(list.get(1)), Double.parseDouble(list.get(2)), list.get(0));
	Point p = new Point(Double.valueOf(list.get(1)), Double.valueOf(list.get(2)), list.get(0));
	System.out.println(p.toString());
	P[curIndex++] = p;
    }

    private static void printPointArray(Point[] arr) {
	for(int i = 0; i < arr.length; i++) 
	    System.out.println(String.format("arr[%d] == %s", i, arr[i].toString()));
    }
    
    private static void die() {
	// Fatal error. Kill program.
	System.out.println("Error parsing input. Exiting.");
	System.exit(-1);
    }    
}

class Point {    
    private final double x;
    private final double y;
    private String name;
    
    public Point(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public String getName() { return name; }

    @Override
    public String toString() {
	return String.format("%s (%f, %f)", getName(), getX(), getY());
    }
}
