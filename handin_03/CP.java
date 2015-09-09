import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;

public class CP {

    private static String pairNumber = "(\\s)*-?[0-9]+(\\s)*-?[0-9]+(\\s)*-?[0-9]+";
    private static String pairName = "[a-zA-Z]+ -?[0-9]+ -?[0-9]+";
    
    private static Pattern patternNumber = Pattern.compile(pairNumber);
    private static Pattern patternName = Pattern.compile(pairName);

    private static int n = 0;
    private static double P[] = new double[10];    
    
    public static void main(String[] args) {
	parseInput();

        Arrays.sort(P);
        
        double Px[] = new double[n/2];
        double Py[] = new double[n/2];
        
        Point result = ClosestPairRec(Px, Py);	
    }

    private static Point ClosestPairRec(double[] px, double[] py) {
	
        return null;
    }

    public static void parseInput() {
	Scanner in = new Scanner(System.in);

	while(true) {
	    if(!in.hasNextLine()) break;

	    String l = in.nextLine();
	    
	    Matcher matcherNumber = patternNumber.matcher(l);
	    Matcher matcherName = patternName.matcher(l);

	    if(matcherNumber.matches()) {
		parseNumberPoint(l);
	    } else if(matcherName.matches()) {
		parseNamePoint(l);
	    }
	}
    }

    public static void parseNamePoint(String l) {
	//	System.out.println("ParseNamePoint: " + l);
	System.out.println("----------------------------------------");
	String[] tmp = l.split(" ");
	System.out.println("Line: " + l);
	for(int i = 0; i < tmp.length; i++)
	    System.out.println(String.format("tmp[%d] = %s", i, tmp[i]));
	System.out.println("----------------------------------------");	
	n++;
    }

    public static void parseNumberPoint(String l) {
	//	System.out.println("ParseNumberPoint: " + l);
	String[] tmp = l.split(" ");
	System.out.println("----------------------------------------");
	System.out.println("Line: " + l);
	for(int i = 0; i < tmp.length; i++)
	    System.out.println(String.format("tmp[%d] = %s", i, tmp[i]));
	System.out.println("----------------------------------------");	
	n++;
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
}
