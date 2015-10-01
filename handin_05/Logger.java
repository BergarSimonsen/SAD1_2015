public class Logger {
    public static boolean debug = false;
    
    public static void log(String msg) {
	if(debug) System.out.println(msg);
    }

    public static void log(int msg) {
	log(String.valueOf(msg));
    }

    public static void log(String[] arr) {
	for(int i = 0; i < arr.length; i++)
	    log(String.format("[%d]: %s%n", i, arr[i]));
    }

    public static void log(Edge[] arr) {
	for(int i = 0; i < arr.length; i++)
	    log(String.format("[%d]: %s%n", i, arr[i].toString()));
    }    
}
