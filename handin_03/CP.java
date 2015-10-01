import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
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
    
    private static final String pairNumber = "(\\s)*-?[0-9]+(\\s)*-?[0-9]+(.[0-9]*)*(\\s)*-?[0-9]+(.[0-9]*)*";
    private static final String pairName = "[a-zA-Z0-9]+ -?[0-9]+(.[0-9]*)* -?[0-9]+(.[0-9]*)*";    
    private static final String dimension = "DIMENSION(\\s)*:(\\s)*[0-9]+";
    private static final String pairScientific = "(\\s)*-?[0-9]+(\\s)*-?[0-9]+-?[0-9]*[.][0-9]*[e][+][0-9]+(\\s)*-?[0-9]*[.][0-9]*[e][+][0-9]+";

    private static final Pattern patternNumber = Pattern.compile(pairNumber);
    private static final Pattern patternName = Pattern.compile(pairName);
    private static final Pattern patternDimension = Pattern.compile(dimension);
    private static final Pattern patternScientific = Pattern.compile(pairScientific);
    
    private static int n = 0;
    private static Point2D.Double P[];
    private static int curIndex = 0;
    
    public static void main(String[] args) {
	if(args.length > 0)
	    System.out.println("filename: " + args[0]);
        parseInput();
        if(P == null) die("Error parsing input. Exiting.");

	//System.out.println(String.format("Dimension: %d, P.length: %d", n, P.length));
        
        Point2D.Double Px[] = new Point2D.Double[n];        
        Point2D.Double Py[] = new Point2D.Double[n];        
        System.arraycopy(P, 0, Px, 0, P.length);
        System.arraycopy(P, 0, Py, 0, P.length);
        Arrays.sort(Px, new PointCmpX());
        Arrays.sort(Py, new PointCmpY());
        
        Point2D.Double[] result = closestPairRec(Px, Py);
        System.out.println(n + " " + getDistance(result[0], result[1]));
    }    
    
    private static Point2D.Double[] closestPairRec(Point2D.Double[] px, Point2D.Double[] py) {
        if (px.length <= 3){
            Point2D.Double[] pair = new Point2D.Double[2];
            double d = Double.POSITIVE_INFINITY;
            for(int i = 0 ; i < px.length ; i++)
                for(int j = i + 1; j < px.length ; j++) {
                    double pairD = getDistance(px[i], px[j]);
                    if (Double.compare(pairD, d) < 0) {
                        pair[0] = px[i];
                        pair[1] = px[j];
                        d = pairD;
                    }
                }
            return pair; 
        } 
        
        int higherHalf = (int) Math.ceil(px.length / 2.0d);
	int lowerHalf = (int) Math.floor(px.length / 2.0d);
        
	Point2D.Double qx[] = new Point2D.Double[higherHalf];
        Point2D.Double qy[] = new Point2D.Double[higherHalf];
        Point2D.Double rx[] = new Point2D.Double[lowerHalf];
        Point2D.Double ry[] = new Point2D.Double[lowerHalf];
        for(int i = 0; i < higherHalf; i++) {
            qx[i] = px[i];
            qy[i] = px[i];
        }
        Arrays.sort(qy, new PointCmpY());
        for(int i = 0; i < lowerHalf ; i++) {
            rx[i] = px[i + higherHalf];
            ry[i] = px[i + higherHalf];
        }
        Arrays.sort(ry, new PointCmpY());
        Point2D.Double pairQ[] = closestPairRec(qx, qy);
        Point2D.Double pairR[] = closestPairRec(rx, ry);
        
        double delta = Math.min(getDistance(pairQ[0], pairQ[1]),
                getDistance(pairR[0], pairR[1]));
        double l = getL(qx);
        
        List<Point2D.Double> sy = new ArrayList<>();
        for (Point2D.Double p : py) {
            BigDecimal bl = new BigDecimal(l);
            BigDecimal bdelta = new BigDecimal(delta);
            BigDecimal left = bl.subtract(bdelta);
            BigDecimal right = bl.add(bdelta);
            if(Double.compare(p.x, left.doubleValue()) >= 0 
                    && Double.compare(p.x, right.doubleValue()) <= 0) {
		sy.add(p);
	    }
        }
        
        double d = Double.POSITIVE_INFINITY;
        Point2D.Double[] pair = new Point2D.Double[2];
        for(int i = 0 ; i < sy.size() ; i++)         
            for(int j = i + 1; j < 16 + i ; j++) {
                if(j >= sy.size()) break;
                double pairD = getDistance(sy.get(i), sy.get(j));
                if(Double.compare(pairD, d) < 0) {
                    d = pairD;
                    pair[0] = sy.get(i);
                    pair[1] = sy.get(j);
                }
            }        
               
        if (Double.compare(d, delta) < 0)     
            return pair;
        else if (Double.compare(getDistance(pairQ[0], pairQ[1]), getDistance(pairR[0], pairR[1])) < 0)
                return pairQ;
        else return pairR;
    }
    
    private static double getDistance(Point2D.Double a, Point2D.Double b) {
        double d = a.distance(b);
        return d;
    }
    
    private static double getL(Point2D.Double[] qx) {
        return qx[qx.length - 1].x;
    }
    
    public static void parseInput() {
	Scanner in = new Scanner(System.in);
	/*      try {
            in = new Scanner(new FileReader("input.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CP.class.getName()).log(Level.SEVERE, null, ex);
	    } */

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
		    die("ERROR Creating array");
	    } else {
		//		System.out.println("no match. line = " + l);
	    }
	}
    }
    
    public static void parseDimension(String l) {
	String[] tmp = l.split(" ");
	n = Integer.parseInt(tmp[tmp.length - 1]);
	P = new Point2D.Double[n];
    }
    
    public static void parsePoint(String l) {
	String[] tmp = l.split(" ");
	List<String> list = new ArrayList<>();

	for(String s : tmp) {
	    if(s != null && s.length() > 0)
		list.add(s);
	}

	//	Point p = new Point(Double.parseDouble(list.get(1)), Double.parseDouble(list.get(2)), list.get(0));
	Point2D.Double p = new Point2D.Double(Double.valueOf(list.get(1)), Double.valueOf(list.get(2)));
	//	System.out.println(p.toString());
	P[curIndex++] = p;
    }
    
    private static void printPointArray(Point2D.Double[] arr) {
	for(int i = 0; i < arr.length; i++) 
	    System.out.println(String.format("arr[%d] == %s", i, arr[i].toString()));
    }
    
    private static void die(String msg) {
	// Fatal error. Kill program.
	System.out.println(msg);
	System.exit(-1);
    }      
}

class PointCmpX implements Comparator<Point2D.Double> {
    @Override
    public int compare(Point2D.Double a, Point2D.Double b) {
        return Double.compare(a.getX(), b.getX());
    }
}

class PointCmpY implements Comparator<Point2D.Double> {
    @Override
    public int compare(Point2D.Double a, Point2D.Double b) {
        return Double.compare(a.getY(), b.getY());
    }
}

