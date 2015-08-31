import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StableMatchingArrays {
    final static int NONE = -1;
    static int n;
    static int menPref[][];
    static int womenPref[][];
    static int womenInvPref[][];
    static String men[];
    static String women[];
    
    public static void main(String[] args) {   
        parseInput();
               
        List<Integer> freeMen = new ArrayList<>();
        for(int i = 0 ; i < n ; i++)
            freeMen.add(i);
        
        int nextWomen[] = new int[n];
        for(int i = 0 ; i < n ; i++)
            nextWomen[i] = 0;
        
        int currentPartners[] = new int[n];
        for(int i = 0 ; i < n ; i++)
            currentPartners[i] = NONE;
        
        //Algorithm
        while(!freeMen.isEmpty()) {
            int m = freeMen.get(0);
            int w = menPref[m][nextWomen[m]];
            nextWomen[m]++;
            
            if(currentPartners[w] == NONE) {
                currentPartners[w] = m;
                freeMen.remove(0);                
            }
            else {
                if(womenInvPref[w][m] < womenInvPref[w][currentPartners[w]]) {
                    freeMen.add(currentPartners[w]);
                    freeMen.remove(0);
                    currentPartners[w] = m;
                }
            }
        }
        
        
        for(int i = 0 ; i < n ; i ++) {
            System.out.print(men[currentPartners[i]] + " -- " + women[i] + "\n");
        }
    }   
    
    private static void parseInput() {
        Scanner in = new Scanner(System.in);
        String p1 = "\\d* .*";
	String p2 = "\\d*(:) .*";
	Pattern r = Pattern.compile(p1);
	Pattern r2 = Pattern.compile(p2);
        
        while(true) {
	    if(!in.hasNextLine()) break;
	    String l = in.nextLine();
	    if(l == null) continue;
	    Matcher m = r.matcher(l);
	    Matcher m1 = r2.matcher(l);
	    if(l.startsWith("#")) continue;
	    else if(l.startsWith("n")) {
		n = Integer.parseInt(l.substring(2));
                men = new String[n];
                women = new String[n];
		menPref = new int[n][n];
		womenPref = new int[n][n];
		womenInvPref = new int[n][n];		
	    } else if(m.matches()) {
		parseNames(l);
	    } else if(m1.matches()) {
		parsePreferenceLists(l);
	    }
	}        
        invertWomensPreferences();

	/*
        n = 4;
        men = new String[] {"Sheldon", "Rajesh", "Howard", "Leonard"};
        women = new String[] {"Amy", "Penny", "Bernadette", "Emily"};
        
        menPref = new int[][] {
            {0, 1, 2, 3},
            {2, 1, 0, 3},
            {2, 1, 3, 0},
            {1, 3, 2, 0}
        };
        
        womenPref = new int[][] {
            {0, 3, 2, 1},
            {3, 1, 0, 2},
            {2, 1, 3, 0},
            {3, 2, 0, 1}
        };
        
        womenInvPref = new int[][] {
            {0, 3, 2, 1},
            {2, 1, 3, 0},
            {3, 1, 0, 2},
            {2, 3, 1, 0}
	    }; */
    }
    
    private static void parseNames(String l) {    
	String[] s = l.split(" ");

	int num = Integer.parseInt(s[0]);
	if(num % 2 == 0)
	    women[(num - 2) / 2] = s[1];
	else
	    men[(num - 1) / 2] = s[1];	
    }
    
    private static void parsePreferenceLists(String l) {    
	String[] s = l.split(" ");
	int index = Integer.parseInt(s[0].substring(0, s[0].length()-1));
	int[] tmp = new int[n];
	
	for(int i = 1; i < s.length; i++) tmp[i - 1] = Integer.parseInt(s[i]);	
	if(index % 2 == 0) {
	    for(int i = 0; i < n; i++)
                womenPref[(index - 2) / 2][i] = ((tmp[i] - 1) / 2);	    
	} else {
	    for(int i = 0; i < n; i++) {
                menPref[(index - 1) / 2][i] = ((tmp[i] - 2) / 2);
	    }
	}
    }
    
    private static void invertWomensPreferences() {        
        for(int i = 0 ; i < n ; i++)
            for(int j = 0 ; j < n ; j++){
                womenInvPref[i][womenPref[i][j]] = j;
            }
    }
}
