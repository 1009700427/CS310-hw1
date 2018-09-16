import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Comparator;
import java.util.HashSet;
public class SearchMap {
    /*
     * @param: arguments
     */
    public static void main(String[] args)
    {
        // checks for input errors
        if(args.length >= 3 || args.length < 2)
        {
            System.out.println("Input format should be: \"java SearchMap input_file output_file\"");
        }
        // the graph
        HashMap<String, HashMap<String, Integer>> graph = new HashMap<String, HashMap<String, Integer>>();

        File file = null;
        Scanner sc = null;
        try{
            file = new File(args[0]);
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
        output(dist, prev, start, args[1]);
    }

    // this method outputs the final result to a file
    public static void output(HashMap<String, Integer> dist, HashMap<String, String> prev, String start, String output)
    {
        int length = 25;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(output, "UTF-8");
        }
        catch(Exception e)
        {
            System.out.println("Output Error!!!");
            return;
        }
        writer.println(String.format("%-25s %-25s %-25s","Destination", "Flight Route From "+start, "Total Cost"));
        for(HashMap.Entry<String, Integer> entry : dist.entrySet())
        {
            if(entry.getKey().equals(start) || entry.getValue()==~(1<<31)) continue;
            writer.println(String.format("%-25s %-25s %-25s", entry.getKey(), getRoute(prev, start, entry.getKey()),
                    "$" + Integer.toString(entry.getValue())));
        }
        writer.close();
    }
    // This function returns the route
    public static String getRoute(HashMap<String, String> prev, String start, String end)
    {
        String res = end;
        String curr = end;
        while(curr!="")
        {
            res = prev.get(curr) + "," + res;
            curr = prev.get(curr);
        }
        return res.substring(1);
    }
}