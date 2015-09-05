import java.util.*;

public class IP {
    private static final boolean debug = false;
    private static int n;
    private static Job[] jobs;
    private static PriorityQueue<Resource> resource;
    private static int cur = 0;
    
    public static void main(String[] args) {
	parseInput();
	resource = new PriorityQueue<>(n, (a, b) -> a.getFree() - b.getFree());	
	Arrays.sort(jobs, (a, b) -> a.getStartTime() - b.getStartTime());

        for (Job job : jobs) {
            if (resource.size() == 0) {
                Resource p = new Resource(cur++);
                p.setFree(job.getEndTime());
                job.setLabel(p.getLabel());
                resource.add(p);
            } else {
                Resource p = resource.peek();
                boolean overlap = isOverlap(job, p.getFree());
                if (overlap) {
                    Resource p2 = new Resource(cur++);
                    p2.setFree(job.getEndTime());
                    job.setLabel(p2.getLabel());
                    resource.add(p2);
                } else {
                    p.setFree(job.getEndTime());
                    job.setLabel(p.getLabel());
                }
            }
        }
	//System.out.println(cur + "\n");

	for(Job j : jobs)
            System.out.println(j.toString());
    }

    public static boolean isOverlap(Job j, int et) {
        return j.getStartTime() < et;
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
	    if(cur < jobs.length) cur++;
	    else break;
	}

	if(debug)
	    for(Job j : jobs)
		log(j.toString());
    }

    public static void log(String m) {
	if(debug) System.out.println(m);
    }

    public static class Job {
	private final int startTime;
	private final int endTime;
	private int label;

	public Job(int startTime, int endTime) {
	    this.startTime = startTime;
	    this.endTime = endTime;
	    this.label = Integer.MIN_VALUE;
	}
        
	public void setLabel(int label) { this.label = label; }
	public int getStartTime() { return startTime; }
	public int getEndTime() { return endTime; }

        @Override
	public String toString() {
	    //	    return String.format("Start time: %d, End time: %d, label: %d", startTime, endTime, label);
	    return String.format("%d %d %d", startTime, endTime, label);	    
	}
    }

    public static class Resource {
	private final int label;
	private int free;

	public Resource(int label) {
	    this.label = label;
	    this.free = Integer.MIN_VALUE;
	}

	public int getLabel() { return label; }        
	public int getFree() { return free; }
	public void setFree(int free) { this.free = free; }
    }
}
