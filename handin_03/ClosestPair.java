import java.util.Arrays;

public class ClosestPair {
    private static final int n = 10;
    private static double P[] = new double[10];
    
    public static void main(String[] args) {
        Arrays.sort(P);
        
        double Px[] = new double[n/2];
        double Py[] = new double[n/2];
        
        Point result = ClosestPairRec(Px, Py);
    }    
    
    private static Point ClosestPairRec(double[] px, double[] py) {
        
        return null;
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

