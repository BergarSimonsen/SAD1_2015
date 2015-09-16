import java.util.*;
public class DP {
    private static ArrayList<Entity> entities;
    private static Entity[] entArr;
    private static Scanner in;

    private static boolean debug = true;
    
    public static void main(String[] args) {
	parseInput();
	entArr = entities.toArray(new Entity[entities.size()]);

	for(Entity e : entArr) {
	    log("---------------------------");
	    log(e.toString());
	    log("---------------------------");	    
	}
    }

    private static void parseInput() {
	entities = new ArrayList<Entity>();
	in = new Scanner(System.in);

	while(in.hasNextLine()) {
	    String l = in.nextLine();
	    if(l.startsWith(">")) {
		parseEntity(l);
	    }
	}
    }

    private static void parseEntity(String l) {
	String[] name = l.split(" ");
	ArrayList<String> data = new ArrayList<String>();
	l = in.nextLine();
	while(!l.startsWith(">")) {
	    data.add(l);
	    if(in.hasNextLine()) {
		l = in.nextLine();
		entities.add(Entity.createEntity(name, data));
	    } else {
		entities.add(Entity.createEntity(name, data));		
		break;
	    }
	}
    }

    private static void log(String msg) {
	if(debug) System.out.println(msg);
    }

    private static void log(int msg) {
	log(String.valueOf(msg));
    }
}

class Entity {
    public String name;
    public String[] data;

    public Entity(String name, int dataElements) {
	this.name = name.substring(1);
	this.data = new String[dataElements];
    }

    public Entity(String name, ArrayList<String> dataList) {
	this.name = name.substring(1);
	this.data = new String[dataList.size()];
	for(int i = 0; i < dataList.size(); i++)
	    this.data[i] = dataList.get(i);
    }

    @Override
    public String toString() {
	String dataString = "";
	for(String s : data)
	    dataString += s + " ";
	return String.format("name: %s, data: %s", name, dataString);
    }

    public static Entity createEntity(String[] name, ArrayList<String> data) {
	String n = "";
	for(String s : name)
	    n += s + " ";
	n = n.substring(0, n.length() - 1); // remove last whitespace
	return new Entity(n, data);
    }    
}
