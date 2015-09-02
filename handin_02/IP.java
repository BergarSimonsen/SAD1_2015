import java.util.*;

public class IP {

    private static boolean debug = true;

    private static ArrayList<ArrayList<Job>> rooms;
    private static int n;
    private static Job[] jobs;
    private static PriorityQueue<Job> priority;
    private static int priorityCapacity = 10;
    
    public static void main(String[] args) {
	rooms = new ArrayList<ArrayList<Job>>();
	parseInput();

	priority = new PriorityQueue<Job>(priorityCapacity, (a, b) -> a.getEndTime() - b.getEndTime());
	System.out.println(" ------------------- ");
	for(Job j : priority)
	    System.out.println(j.toString());
	System.out.println(" ------------------- ");	

	Arrays.sort(jobs, (a, b) -> a.getStartTime() - b.getStartTime());
	
	for(Job j : jobs)
	    System.out.println(j.toString());
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

	if(debug)
	    for(Job j : jobs)
		log(j.toString());
    }

    public static void addNewRoom() {
	if(rooms != null)
	    rooms.add(new ArrayList<Job>());
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
