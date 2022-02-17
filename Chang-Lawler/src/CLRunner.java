import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CLRunner {

    public static void main(String[] args) {

        try {
            int k = Integer.parseInt(args[0]);
            String T = args[1], P = args[2], msFile = args[3], rgFile = args[4];
            try {
                BufferedReader reader = new BufferedReader(new FileReader(T));
                String text;
                while ((text = reader.readLine()) != null) {
                    if (!text.startsWith("#"))
                        break;
                }
                reader.close();
                reader = new BufferedReader(new FileReader(P));
                String pattern;
                while ((pattern = reader.readLine()) != null) {
                    if (!pattern.startsWith("#"))
                        break;
                }
                if (text != null && pattern != null) {
                    CLAlgo algo = new CLAlgo(k, text, pattern);
                    algo.stringSearch(msFile, rgFile);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("<k> <in:text-file> <in:pattern-file> <out:ms(j).tsv> <out:surviving regions.txt>");
        }
    }
}
