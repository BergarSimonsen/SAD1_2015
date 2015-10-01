import java.util.*;
import java.io.*;
import java.lang.Math.*;
public class Validator {

    private static ArrayList<Res> first = new ArrayList<Res>();
    private static ArrayList<Res> second = new ArrayList<Res>();

    public static void main(String[] args) {
	try {
	    File f1 = new File(args[0]);
	    File f2 = new File(args[1]);

	    first = readFile(f1);
	    second = readFile(f2);

	    if(first.size() != second.size()) {
		System.out.println("Not equal");
		System.exit(-1);
	    }
		
	    boolean identical = true;

	    System.out.println("---------------------------");
	    System.out.println(first.size() + " " + second.size());
	    System.out.println("---------------------------");	    
	    
	    for(int i = 0; i < first.size(); i++) {
		String s1 = first.get(i).toString();
		String s2 = second.get(i).toString();

		identical = s1.equals(s2);

		//		if(!identical)
		System.out.println(String.format("[%d]: %s | %s | %b", i, first.get(i).toString(), second.get(i).toString(), identical));
		
		/*		if(!s1.equals(s2)) {
		    System.out.println("[" + i + "]: " + first.get(i).toString() + " ||| " + second.get(i).toString());
		    identical = false;
		    break;
		    } */
	    }

	    System.out.println("Files are identical: " + identical);
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }

    private static ArrayList<Res> readFile(File fin) throws IOException {
	ArrayList<Res> r = new ArrayList<Res>();
	FileInputStream fis = new FileInputStream(fin);
	
	//Construct BufferedReader from InputStreamReader
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	
	String line = null;
	while ((line = br.readLine()) != null) {
	    String[] tmp = line.split(" ");
	    //r.add(new Res(Math.floor(Double.parseDouble(tmp[0])), Math.floor(Double.parseDouble(tmp[1]))));
	    r.add(new Res(Double.parseDouble(tmp[0]), Double.parseDouble(tmp[1])));
	}
 
	br.close();
	return r;
    }
}

class Res {
    public double x;
    public double y;

    public Res(double x, double y) {
	this.x = x;
	this.y = y;
    }

    @Override
    public String toString() {
	return String.format("%f %f", x, y);
    }

    @Override
    public boolean equals(Object aThat) {
	Res tmp = (Res) aThat;
	return x == tmp.x && y == tmp.y;
    }

}
