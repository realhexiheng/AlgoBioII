/**
 * @Author Xiheng He
 * @ClassName HashRunner
 * @Description TODO
 * @Date 2021/11/2021/11/14/9:26
 * @Version 1.0
 */
public class HashRunner {

    public static void main(String[] args) {
        try {
            int k;
            if (Integer.parseInt(args[0]) <= 0) {
                throw new NumberFormatException("k must be positive Integer");
            } else
                k = Integer.parseInt(args[0]);
            String seq = args[1];
            String query = args[2];
            String path = args[3];
            FastaHash fastaHash = new FastaHash(k, seq);
            fastaHash.computeOffset(query);
            fastaHash.outputOffSets(path);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Usage: java -jar blatt3_4.jar k dbseq_0,...,dbseq_n query output");
        }
    }
}
