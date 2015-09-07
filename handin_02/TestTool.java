import java.util.*;
import java.io.*;
public class TestTool {

    private static ArrayList<String> c1;
    private static ArrayList<String> c2;
    private static Set<String> result;

    public static void main(String[] args) {
	if(args.length < 2) {
	    System.out.println("Usage: file1, file2");
	    System.exit(0);
	}

	try {
	    File first = new File(args[0]);
	    File second = new File(args[1]);

	    c1 = readFile(first);
	    c2 = readFile(second);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	if(c1 != null && c2 != null) {
	    System.out.println("Length of first: " + c1.size());
	    System.out.println("Length of second: " + c2.size());

	    result = new HashSet<String>();

	    for(String s : c1)
		result.add(s);

	    for(String s : c2)
		result.add(s);

	    System.out.println("----------------------");
	    System.out.println("Length of set: " + result.size());
	    System.out.println("----------------------");	    
	}
    }
    
    
    private static ArrayList<String> readFile(File fin) throws IOException {
	ArrayList<String> r = new ArrayList<String>();
	FileInputStream fis = new FileInputStream(fin);
	
	//Construct BufferedReader from InputStreamReader
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	
	String line = null;
	while ((line = br.readLine()) != null) {
	    r.add(line);
	}
 
	br.close();
	return r;
    }
}
