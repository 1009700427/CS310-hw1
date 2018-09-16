import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Formatter;
import java.io.PrintWriter;
public class FlightMap {
    // The main function for solving the map the yield the requested output
    // Implements Dijkstra's Algorithm
    public static void solveMap(HashMap<String, HashMap<String, Integer>> graph, HashMap<String, Integer> dist,
                                HashSet<String> unvisited, HashMap<String, String> prev)
    {
        while(!unvisited.isEmpty())
        {
            // currNode is the parent node for which we are operating on
            String currNode = findSmallestDist(dist, unvisited);
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
    // Finds the unvisited node with the smallest distance thus far
    public static String findSmallestDist(HashMap<String, Integer> dist, HashSet<String> unvisited)
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
