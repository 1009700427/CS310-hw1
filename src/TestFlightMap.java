import org.junit.*;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class TestFlightMap {
    private HashMap<String, String> _prev;
    private HashMap<String, Integer> _dist;
    private HashSet<String> _unvisited;
    @Before
    public void setup()
    {
        this._prev = new HashMap<String, String>();
        this._prev.put("P","");
        this._prev.put("Q","");
        this._prev.put("R","P");
        this._prev.put("S","W");
        this._prev.put("T","S");
        this._prev.put("W","P");
        this._prev.put("X","R");
        this._prev.put("Y","W");
        this._prev.put("Z","Y");

        this._dist = new HashMap<String, Integer>();
        this._dist.put("P",0);
        this._dist.put("Q",~(1<<31));
        this._dist.put("R",300);
        this._dist.put("S",450);
        this._dist.put("T",750);
        this._dist.put("W",200);
        this._dist.put("X",500);
        this._dist.put("Y",700);
        this._dist.put("Z",1150);

        this._unvisited = new HashSet<String>();
        this._unvisited.add("R");
        this._unvisited.add("S");
        this._unvisited.add("W");
    }
    @Test
    public void solveMapTest()
    {
        HashMap<String, HashMap<String, Integer>> graph = new HashMap<String, HashMap<String, Integer>>();
        File file = null;
        Scanner sc = null;
        try{
            file = new File("./input/inputfile.txt");
            sc = new Scanner(file);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Input File Not Found!");
            return;
        }
        String start = "";
        if(sc.hasNextLine())
        {
            start = sc.nextLine();
        }
        // records the distance from origin to any node
        //
        HashMap<String, Integer> dist = new HashMap<String, Integer>();
        // records the unvisited nodes
        HashSet<String> unvisited = new HashSet<String>();
        // records the previous vertex of a node
        HashMap<String, String> prev = new HashMap<String, String>();
        // distance from source vertex to itself is 0
        dist.put(start, 0);
        unvisited.add(start);
        prev.put(start, "");
        // constructing graph
        while(sc.hasNextLine())
        {
            String[] line = sc.nextLine().split(" ");
            if(!graph.containsKey(line[0]))
            {
                HashMap<String, Integer> temp = new HashMap<String, Integer>();
                temp.put(line[1], Integer.parseInt(line[2]));
                graph.put(line[0], temp);
                // sets all other distanced to INT_MAX
                if(!unvisited.contains(line[1]))
                {
                    dist.put(line[1], ~(1<<31));
                    unvisited.add(line[1]);
                    prev.put(line[1], "");
                }
                if(!unvisited.contains(line[0]))
                {
                    dist.put(line[0], ~(1<<31));
                    unvisited.add(line[0]);
                    prev.put(line[0], "");
                }
            }
            else
            {
                graph.get(line[0]).put(line[1], Integer.parseInt(line[2]));
            }
        }
        FlightMap.solveMap(graph, dist, unvisited, prev);
        assertTrue(this._prev.equals(prev));
        assertTrue(this._dist.equals(dist));
    }

    @Test
    public void findSmallestDistTest()
    {
        assertTrue("W".equals(FlightMap.findSmallestDist(this._dist, this._unvisited)));
    }
}
