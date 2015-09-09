import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CP {
    private static final String pairNumber = "(\\s)*-?[0-9]+(\\s)*-?[0-9]+(\\s)*-?[0-9]+";
    private static final String pairName = "[a-zA-Z]+ -?[0-9]+ -?[0-9]+";
    private static final String dimension = "DIMENSION: [0-9]+";

    private static final Pattern patternNumber = Pattern.compile(pairNumber);
    private static final Pattern patternName = Pattern.compile(pairName);
    private static final Pattern patternDimension = Pattern.compile(dimension);
    private static int n = 0;
    private static Point P[];
    private static int curIndex = 0;
    
    
    public static void main(String[] args) {
        parseInput();

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

    private static void printPointArray(Point[] arr) {
	for(int i = 0; i < arr.length; i++) 
	    System.out.println(String.format("arr[%d] == %s", i, arr[i].toString()));
    }
    
    public static void parseInput() {
	Scanner in = new Scanner(System.in);

	while(true) {
	    if(!in.hasNextLine()) break;

	    String l = in.nextLine();
	    
	    Matcher matcherNumber = patternNumber.matcher(l);
	    Matcher matcherName = patternName.matcher(l);
	    Matcher matcherDimension = patternDimension.matcher(l);

	    if(matcherDimension.matches()) {
		parseDimension(l);
	    } else if(matcherNumber.matches() || matcherName.matches()) {
		parsePoint(l);
	    } 
	}
    }

    public static void parseDimension(String l) {
	String[] tmp = l.split(" ");
	n = Integer.parseInt(tmp[1]);
	P = new Point[n];
    }
    
    /*    public static void parseNamePoint(String l) {
	
	System.out.println("----------------------------------------");
	String[] tmp = l.split(" ");
	System.out.println("Line: " + l);
	for(int i = 0; i < tmp.length; i++)
	    System.out.println(String.format("tmp[%d] = %s", i, tmp[i]));
	System.out.println("----------------------------------------");	
	n++; 
    }*/

    /*    public static void parseNumberPoint(String l) {
	String[] tmp = l.split(" ");
	if(tmp.length == 3) {
	    Point p = new Point(tmp[0], Double.parseDouble(tmp[1]), Double.parseDouble(tmp[2]));
	    P[curIndex++] = p;
	}
    } */

    public static void parsePoint(String l) {
	String[] tmp = l.split(" ");
	//	System.out.println("tmp.length == " + tmp.length);

	ArrayList<String> list = new ArrayList<String>();

	for(String s : tmp) {
	    if(s != null && s.length() > 0)
		list.add(s);
	}

	Point p = new Point(Double.parseDouble(list.get(1)), Double.parseDouble(list.get(2)), list.get(0));
	P[curIndex++] = p;
	
	/*	if(tmp.length == 3) {
	    Point p = new Point(Double.parseDouble(tmp[1]), Double.parseDouble(tmp[2]), tmp[0]);
	    P[curIndex++] = p;
	} else {
	    for(int i = 0; i < tmp.length; i++)
		System.out.println(String.format("tmp[%d] == %s", i, tmp[i]));
		} */
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
