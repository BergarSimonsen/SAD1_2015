import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        Point Px[] = new Point[n];        
        Point Py[] = new Point[n];        
        System.arraycopy(P, 0, Px, 0, P.length);
        System.arraycopy(P, 0, Py, 0, P.length);
        System.out.println("After creating Px & Py");
        Arrays.sort(Px, new PointCmpX());
        Arrays.sort(Py, new PointCmpY());
        
        Point[] result = closestPairRec(Px, Py);
        System.out.println(n + " " + getDistance(result[0], result[1]));
    }    
    
    private static Point[] closestPairRec(Point[] px, Point[] py) {
        if (px.length <= 3){
            Point[] pair = new Point[2];
            double d = Double.MAX_VALUE;
            for(int i = 0 ; i < px.length ; i++)
                for(int j = i + 1 ; j < px.length ; j++) {
                    double pairD = getDistance(px[i], px[j]);
                    if (pairD < d) {
                        pair[0] = px[i];
                        pair[1] = px[j];
                        d = pairD;
                    }
                }
            return pair; 
        }  
        Point qx[] = new Point[(int)Math.floor(px.length/2.0d)];
        Point qy[] = new Point[(int)Math.floor(px.length/2.0d)];
        Point rx[] = new Point[(int)Math.ceil(px.length/2.0d)];
        Point ry[] = new Point[(int)Math.ceil(px.length/2.0d)];
        for(int i = 0 ; i < (int)Math.floor(px.length/2.0d) ; i++) {
            qx[i] = px[i];
            qy[i] = py[i];
        }        
        for(int i = (int)Math.floor(px.length/2.0d) ; i < px.length ; i++) {
            rx[i - (int)Math.floor(px.length/2.0d)] = px[i];
            ry[i - (int)Math.floor(px.length/2.0d)] = py[i];
        }
        
        Point pairQ[] = closestPairRec(qx, qy);
        Point pairR[] = closestPairRec(rx, ry);
        
        double delta = Math.min(getDistance(pairQ[0], pairQ[1]),
                getDistance(pairR[0], pairR[1]));
        Point l = getMaxXPoint(qx);
        
        List<Point> sy = new ArrayList<>();
        for (Point p : py) {
            if (getDistance(l, p) <= delta) {
                sy.add(p);
            }
        }
        
        double d = Double.POSITIVE_INFINITY;
        Point[] pair = new Point[2];
        for(int i = 0 ; i < sy.size() ; i++)         
            for(int j = i + 1 ; j < 15 ; j++) {
                if(j >= sy.size()) break;
                double pairD = getDistance(sy.get(i), sy.get(j));
                if(pairD < d) {
                    d = pairD;
                    pair[0] = sy.get(i);
                    pair[1] = sy.get(j);
                }
            }
        
        if (d < delta)     
            return pair;
        else if (getDistance(pairQ[0], pairQ[1]) <= getDistance(pairR[0], pairR[1]))
                return pairQ;
        else return pairR;
    }
    
    private static double getDistance(Point a, Point b) {
        double d = Math.sqrt(Math.pow(b.getX() - a.getX(), 2) 
                + Math.pow(b.getY() - a.getY(), 2));
        return d;
    }
    
    private static Point getMaxXPoint(Point[] qx) {
        Point pMax = null;
        double max = Double.NEGATIVE_INFINITY;
        for (Point p : qx) {
            if (p.getX() > max) {
                pMax = p;
            }
        }
        if(pMax == null) die();
        return pMax;
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
	List<String> list = new ArrayList<>();

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
    private final String name;
    
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

class PointCmpX implements Comparator<Point> {
    @Override
    public int compare(Point a, Point b) {
        return Double.compare(a.getX(), b.getX());
    }
}

class PointCmpY implements Comparator<Point> {
    @Override
    public int compare(Point a, Point b) {
        return Double.compare(a.getY(), b.getY());
    }
}

