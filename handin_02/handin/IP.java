import java.util.*;

public class IP {

    private static boolean debug = false;

    private static int n;
    private static Job[] jobs;
    private static PriorityQueue<Priority> priority;
    private static int priorityCapacity = 10; // remove

    private static int cur = 0;
    
    public static void main(String[] args) {
	parseInput();
	priority = new PriorityQueue<Priority>(n, (a, b) -> a.getFree() - b.getFree());	
	Arrays.sort(jobs, (a, b) -> a.getStartTime() - b.getStartTime());

	// TODO: Change label / index name
	
	//	while(true) {
	    for(int i = 0; i < jobs.length; i++) {
		if(priority.size() == 0) {
		    Priority p = new Priority(cur++);
		    p.setFree(jobs[i].getEndTime());
		    jobs[i].setLabel(p.getIndex());
		    priority.add(p);
		} else {
		    Priority p = priority.poll();
		    boolean overlap = isOverlap(jobs[i], p.getFree());

		    if(overlap) {
			Priority p2 = new Priority(cur++);
			p2.setFree(jobs[i].getEndTime());
			jobs[i].setLabel(p2.getIndex());
			priority.add(p2);
			priority.add(p);
		    } else {
			p.setFree(jobs[i].getEndTime());
			jobs[i].setLabel(p.getIndex());
			priority.add(p);
		    }
		}
	    }
	    //	}

	    System.out.println(cur + "\n");

	    for(Job j : jobs)
		System.out.println(j.toString());
    }

    public static boolean isOverlap(Job j, int et) {
	if(j.getStartTime() < et) return true;
	else return false;
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

    public static void log(String m) {
	if(debug) System.out.println(m);
    }

    public static class Job {
	private int startTime;
	private int endTime;
	private int label;

	public Job(int startTime, int endTime) {
	    this.startTime = startTime;
	    this.endTime = endTime;
	    this.label = Integer.MIN_VALUE;
	}

	public int getLabel() { return label; }
	public void setLabel(int label) { this.label = label; }

	public int getStartTime() { return startTime; }
	public void setStartTime(int startTime) { this.startTime = startTime; }

	public int getEndTime() { return endTime; }
	public void setEndTime(int endTime) { this.endTime = endTime; }

	public String toString() {
	    //	    return String.format("Start time: %d, End time: %d, label: %d", startTime, endTime, label);
	    return String.format("%d %d %d", startTime, endTime, label);	    
	}
    }

    public static class Priority {
	private int index;
	private int free;

	public Priority(int index) {
	    this.index = index;
	    this.free = Integer.MIN_VALUE;
	}

	public int getIndex() { return index; }
	public void setIndex(int index) { this.index = index; }

	public int getFree() { return free; }
	public void setFree(int free) { this.free = free; }
    }
}
