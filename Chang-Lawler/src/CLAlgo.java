import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.TreeSet;

public class CLAlgo {

    private final int k;
    private final String text;
    private final String pattern;
    private int tLen;
    private int pLen;

    public CLAlgo(int k, String text, String pattern) {
        this.k = k;
        this.text = text;
        this.pattern = pattern;
        this.tLen = text.length();
        this.pLen = pattern.length();
    }

    private int computeMS(int i_t_a) {
        for (int i_t_e = (i_t_a + pLen) > tLen ? tLen : i_t_a + pLen; i_t_e >= i_t_a; i_t_e--)
        {
            String substr_text = text.substring(i_t_a, i_t_e);
            if (pattern.contains(substr_text)) return substr_text.length();
        }
        return 0;
    }

    public void stringSearch(String mismatchFile, String regionFile) {
        TreeSet<Integer> regions = new TreeSet<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mismatchFile))) {
            writer.write('j');
            writer.write("\t " + "ms(j)");
            writer.write("\n");
            for (int j_star = 0; j_star < tLen; j_star += (pLen + 1) / 2) {
                int j = j_star;
                int count = 0;
                while (count <= k && ((j - j_star) <= (pLen + 1) / 2) && j <= tLen) {
                    int ms = computeMS(j);
                    writer.write(j + "\t" + ms);
                    writer.write("\n");
                    j += ms + 1;
                    count++;
                }
                if (j - j_star > (pLen + 1) / 2 || (tLen - j_star < (pLen + 1) / 2 && j >
                        tLen)) regions.add(j_star);
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(regionFile))) {
            writer.write("surviving regions (k=" + k + ")");
            writer.write("\n");
            writer.write(regions.toString());
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
