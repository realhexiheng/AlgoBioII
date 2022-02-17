import java.io.*;
import java.util.*;

/**
 * @Author Xiheng He
 * @ClassName Fasta
 * @Description TODO
 * @Date 2021/11/2021/11/22/7:26
 * @Version 1.0
 */
public class Fasta extends FastaHash {

    private HashMap<String, Integer> matrix;
    private TreeMap<Integer, LinkedList<Integer>> hotspotMatches;

    public Fasta(int k, String dbSeq) {
        super(k, dbSeq);
        this.hotspotMatches = new TreeMap<>();
    }

    public void setMatrix(String matrix) {
        try {
            BufferedReader matrixReader = new BufferedReader(new FileReader(matrix));
            this.matrix = new HashMap<>();
            ArrayList<String> aa = new ArrayList<>();
            String line;
            String[] temp;
            while ((line = matrixReader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    temp = line.split("\t");
                    aa.add(temp[0]);
                    for (int i = 1; i < temp.length; i++) {
                        this.matrix.put(temp[0] + aa.get(i - 1), Integer.parseInt(temp[i]));
                        this.matrix.put(aa.get(i - 1) + temp[0], Integer.parseInt(temp[i]));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Matrix file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void computeOffset() {
        this.offsetVector = new int[query.length() + this.dbSeq.length() - 1];
        for (int i = 0; i < this.query.length() - k + 1; i++) {
            String kMer = this.query.substring(i, i + k);
            LinkedList<Integer> dbPos = dataBase.get(kMer);
            if (dbPos != null) {
                for (Integer index : dbPos) {
                    this.offsetVector[index - i + this.query.length() - 1] ++;
                    LinkedList<Integer> matches = this.hotspotMatches.putIfAbsent(index - i, new LinkedList<>());
                    if (matches == null) matches = this.hotspotMatches.get(index - i);
                    matches.add(i);
                }
            }
        }
    }

    private int computeScore(List<Integer> hotspots) {
        int spots = 0;
        Iterator<Integer> itr = hotspots.iterator();
        Integer lastSpot = itr.next();
        while (itr.hasNext()) {
            Integer currentSpot = itr.next();
            if (currentSpot - lastSpot - this.k > 0) {
                spots += currentSpot - lastSpot - this.k;
                lastSpot = currentSpot;
            }
        }
        return hotspots.size() - spots;
    }

    public void runDiagonals(String query, int n, String output) {
        this.query = query;
        this.computeOffset();
        TreeSet<Run> allRuns = new TreeSet<>();
        try {
            BufferedWriter listWriter = new BufferedWriter(new FileWriter(output + ".list"));
            for (Map.Entry<Integer, LinkedList<Integer>> entry : this.hotspotMatches.entrySet()) {
                Integer offSetPos = entry.getKey();
                LinkedList<Integer> matches = entry.getValue();
                listWriter.write(offSetPos + "\t" + matches.toString() + "\n");

                for (int i = 0; i < matches.size(); i++) {
                    for (int j = i; j < matches.size(); j++) {
                        int length = matches.get(j) - matches.get(i) + this.k;
                        Run currentRun = new Run(this.dbSeq.substring(offSetPos + matches.get(i), offSetPos + matches.get(i) +
                                length),query.substring(matches.get(i), matches.get(i) + length), matches.get(i), offSetPos, length);
                        if (this.computeScore(matches.subList(i, j + 1)) >= 0) {
                            currentRun.computeInitScore(this.matrix);
                            allRuns.add(currentRun);
                        }
                    }
                }
            }
            listWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot write to list");
        }

        try {
            BufferedWriter runWriter = new BufferedWriter(new FileWriter(output + ".runs"));
            Iterator<Run> itr = allRuns.descendingIterator();
            int counter = 0;
            while (itr.hasNext() && n > counter) {
                runWriter.write(itr.next().toString() + "\n");
                counter ++;
            }
            runWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Can not write to run");
        }
    }


    class Run implements Comparable<Run> {

        private final String dbSubStr;
        private final String querySubStr;
        private final int queryStart;
        private final int offSetStart;
        private final int length;
        private double initScore;

        Run(String dbSubStr, String querySubStr, int queryStart, int offSetStart, int length) {
            this.dbSubStr = dbSubStr;
            this.querySubStr = querySubStr;
            this.queryStart = queryStart;
            this.offSetStart = offSetStart;
            this.length = length;
            this.initScore = 0;
        }

        public void computeInitScore(HashMap<String,Integer> matrix) {
            for (int i = 0; i < dbSubStr.length(); i++) {
                this.initScore += matrix.get(dbSubStr.charAt(i) + "" + querySubStr.charAt(i));
            }
        }

        @Override
        public int compareTo(Run o) {
            if (this.equals(o))
                return 0;
            if (this.initScore > o.initScore)
                return 1;
            if (this.initScore < o.initScore)
                return -1;
            if (this.queryStart < o.queryStart)
                return 1;
            if (this.queryStart > o.queryStart)
                return -1;
            if (this.offSetStart < o.offSetStart)
                return 1;
            if (this.offSetStart > o.offSetStart)
                return -1;
            return Integer.compare(this.length, o.length);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || !getClass().equals(obj.getClass()))
                return false;
            Run anotherRun = (Run) obj;
            return queryStart == anotherRun.queryStart && offSetStart == anotherRun.offSetStart
                    && length == anotherRun.length && initScore == anotherRun.initScore && Objects.equals(querySubStr, anotherRun.querySubStr)
                    && Objects.equals(dbSubStr, anotherRun.dbSubStr);
        }

        @Override
        public int hashCode() {
            return Objects.hash(queryStart, offSetStart, length, initScore, dbSubStr, querySubStr);
        }

        @Override
        public String toString() {
            return queryStart + "\t" + offSetStart + "\t" + length + "\t" + initScore + "\t" + dbSubStr + "\t" + querySubStr;
        }
    }
}