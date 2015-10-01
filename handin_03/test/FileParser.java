import java.util.*;
public class FileParser {
    
    public static void main(String[] args) {
	Scanner in = new Scanner(System.in);

	System.out.println("#!/bin/bash");
	while(in.hasNextLine()) {
	    String l = in.nextLine();
	    String[] tmp = l.split(" ");
	    String[] tmp2 = tmp[0].split("/");
	    String s = tmp2[tmp2.length - 1].substring(0, tmp2[tmp2.length - 1].length() - 2);
	    s = s.replace(".", "-");
	    System.out.println("java CP < data/" + s + "p.txt");
	    
	    /*	    for(int i = 0; i < tmp2.length; i++)
		System.out.print(tmp2[i] + " ");
		System.out.println(); */
	}
    }
}
