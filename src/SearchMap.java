public class SearchMap {
    public static void main(String[] args)
    {
        // checks for input errors
        if(args.length >= 3 || args.length < 2)
        {
            System.out.println("Input format should be: \"java SearchMap input_file output_file\"");
        }
    }
}
