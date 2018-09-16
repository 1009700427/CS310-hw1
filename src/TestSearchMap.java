import org.junit.Before;
import org.junit.Test;
import org.apache.commons.io.*;
import java.util.HashMap;
import java.io.File;

import static org.junit.Assert.*;

public class TestSearchMap {
    private HashMap<String, String> prev;
    private HashMap<String, Integer> dist;
    @Before
    public void setup()
    {
        this.prev = new HashMap<String, String>();
        this.prev.put("P","");
        this.prev.put("Q","");
        this.prev.put("R","P");
        this.prev.put("S","W");
        this.prev.put("T","S");
        this.prev.put("W","P");
        this.prev.put("X","R");
        this.prev.put("Y","W");
        this.prev.put("Z","Y");

        this.dist = new HashMap<String, Integer>();
        this.dist.put("P",0);
        this.dist.put("Q",~(1<<31));
        this.dist.put("R",300);
        this.dist.put("S",450);
        this.dist.put("T",750);
        this.dist.put("W",200);
        this.dist.put("X",500);
        this.dist.put("Y",700);
        this.dist.put("Z",1150);
    }
    @Test
    public void getRouteTest()
    {
        assertEquals(SearchMap.getRoute(prev, "P", "R"), "P,R");
        assertEquals(SearchMap.getRoute(prev, "P", "T"), "P,W,S,T");
        assertEquals(SearchMap.getRoute(prev, "P", "Z"), "P,W,Y,Z");
        assertEquals(SearchMap.getRoute(prev, "P", "Y"), "P,W,Y");
    }
    @Test
    public void outputTest() throws Exception {
        SearchMap.output(dist, prev, "P", "../output.txt");

        File f1 = new File("../output.txt");
        File f2 = new File("../test/output.txt");
        assertEquals(f1.toString(),f1.toString());
    }
    @Test
    public void mainTest() throws Exception {
        String[]args = {"./input.txt", "output.txt"};
        SearchMap.main(args);
        File f1 = new File("../output.txt");
        File f2 = new File("../test/output.txt");
        assertEquals(f1.toString(),f1.toString());
    }
}