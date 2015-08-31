import java.util.*;

public class IP {

    private static boolean debug = true;

    private static ArrayList<Job> rooms;
    private static int n;
    private static Job[] jobs;
    

    public static void main(String[] args) {
	rooms = new ArrayList<Job>();
	parseInput();
    }

    public static void parseInput() {
	Scanner in = new Scanner(System.in);

	String firstLine = in.nextLine();
	n = Integer.parseInt(firstLine);
	jobs = new Job[n];

	int cur = 0;

	while(in.hasNextLine()) {
	    String l = in.nextLine();
	    if(l == null || l.length() == 0) continue;
	    String[] input = l.split(" ");

	    jobs[cur] = new Job(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
	    if(cur < jobs.length)
		cur++;
	    else
		break;
	}

	for(Job j : jobs)
	    System.out.println(j.toString());
    }

    public static void log(String m) {
	if(debug) System.out.println(m);
    }

    public static class Job {
	private int startTime;
	private int endTime;

	public Job(int startTime, int endTime) {
	    this.startTime = startTime;
	    this.endTime = endTime;
	}

	public int getStartTime() { return startTime; }
	public void setStartTime(int startTime) { this.startTime = startTime; }

	public int getEndTime() { return endTime; }
	public void setEndTime(int endTime) { this.endTime = endTime; }

	public String toString() {
	    return String.format("Start time: %d, End time: %d", startTime, endTime);
	}
    }
}
