import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Formatter;
import java.io.PrintWriter;
public class FlightMap {
    public static void solveMap(HashMap<String, HashMap<String, Integer>> graph, HashMap<String, Integer> dist,
                                HashSet<String> unvisited, HashMap<String, String> prev)
    {
        while(!unvisited.isEmpty())
        {
//            for(HashMap.Entry<String, String> entry : prev.entrySet())
//            {
//                System.out.println(entry.getKey() + " " + entry.getValue());
//            }
            // currNode is the parent node for which we are operating on
            String currNode = findSmallestDist(dist, unvisited);
//            System.out.println(currNode);
//            System.out.println("");
            if(currNode=="") break;
            unvisited.remove(currNode);
            // when there is no edge pointing out of this node
            // avoids nullptr exception
            if(!graph.containsKey(currNode)) continue;
            HashMap<String, Integer> children = graph.get(currNode);
            // now iterates through the children
            for(HashMap.Entry<String, Integer> entry : children.entrySet())
            {
                // when the node is already visited
                if(!unvisited.contains(entry.getKey())) continue;
                // now get the edge weight
                String childNode = entry.getKey();
                int price = entry.getValue();
                // if the weight is smaller than what we had before
                // update the weight
                if(price + dist.get(currNode) < dist.get(childNode))
                {
                    dist.put(childNode, price + dist.get(currNode));
                    prev.put(childNode, currNode);
                }
            }
        }
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
    private static String getRoute(HashMap<String, String> prev, String start, String end)
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

    private static String findSmallestDist(HashMap<String, Integer> dist, HashSet<String> unvisited)
    {
        String res = "";
        int min = ~(1<<31);
        for(HashMap.Entry<String, Integer> entry : dist.entrySet())
        {
            // must be an unvisited node
            if(entry.getValue() < min && unvisited.contains(entry.getKey()))
            {
                min = entry.getValue();
                res = entry.getKey();
            }
        }
        return res;
    }
}
