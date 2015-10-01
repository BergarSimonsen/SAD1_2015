import java.util.Scanner;
public class Creator {
    public static void main(String[] args) {
	Scanner in = new Scanner(System.in);
	while(in.hasNextLine()) {
	    String l = in.nextLine();
	    System.out.println(String.format("java CP %s < data/%s", l, l));
	}
    }
}
