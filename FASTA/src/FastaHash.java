import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @Author Xiheng He
 * @ClassName FastaHash
 * @Description TODO
 * @Date 2021/11/2021/11/14/9:18
 * @Version 1.0
 */
public class FastaHash {

    protected final int k;
    protected String dbSeq;
    protected String query;
    protected TreeMap<String, LinkedList<Integer>> dataBase;
    protected int[] offsetVector;

    public FastaHash(int k, String dbSeq) {
        this.k = k;
        this.dbSeq = dbSeq;
        this.dataBase = new TreeMap<>();
        for (int i = 0; i < dbSeq.length() - k + 1; i++) {
            String kMer = dbSeq.substring(i, i + k);
            LinkedList<Integer> dbPos = dataBase.putIfAbsent(kMer, new LinkedList<>());
            if (dbPos == null)
                dbPos = dataBase.get(kMer);
            dbPos.add(i);
        }
    }

    public void computeOffset(String query) {
        this.query = query;
        this.offsetVector = new int[query.length() + this.dbSeq.length() - 1];
        for (int i = 0; i < query.length() - k + 1; i++) {
            String kMer = query.substring(i, i + k);
            LinkedList<Integer> dbPos = dataBase.get(kMer);
            if (dbPos != null) {
                for (Integer index : dbPos) {
                    this.offsetVector[index - i + query.length() - 1] ++;
                }
            }
        }
    }

    public void outputOffSets(String path) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(path + ".list"));
            for (Map.Entry<String, LinkedList<Integer>> entry : dataBase.entrySet()) {
                writer.write(entry.getKey() + "\t" + entry.getValue().toString());
                writer.newLine();
            }
            writer.close();
            writer = new BufferedWriter(new FileWriter(path + ".vector"));
            int[] header = new int[this.query.length() + this.dbSeq.length() - 1];
            for (int i = 0; i < header.length; i++)
                header[i] = i - this.query.length() + 1;
            writer.write(Arrays.toString(header));
            writer.newLine();
            writer.write(Arrays.toString(offsetVector));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
