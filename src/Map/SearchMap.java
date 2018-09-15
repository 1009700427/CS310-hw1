package Map;

import Map.*;
public class SearchMap {
    /*
     * @param: arguments
     */
    public static void main(String[] args)
    {
        FlightMap.printMSG();
        // checks for input errors
        if(args.length >= 3 || args.length < 2)
        {
            System.out.println("Input format should be: \"java Map.SearchMap input_file output_file\"");
        }
    }
}
