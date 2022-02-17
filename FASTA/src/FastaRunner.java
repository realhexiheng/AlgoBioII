/**
 * @Author Xiheng He
 * @ClassName FastaRunner
 * @Description TODO
 * @Date 2021/11/2021/11/22/7:20
 * @Version 1.0
 */
public class FastaRunner {

    public static void main(String[] args) {
        try {
            int k, n;
            if (Integer.parseInt(args[0]) > 0)
                k = Integer.parseInt(args[0]);
            else
                throw new NumberFormatException("give a positive value for k");
            if (Integer.parseInt(args[4]) > 0)
                n = Integer.parseInt(args[4]);
            else
                throw new NumberFormatException("give a positive value for n");
            String seq = args[1];
            String query = args[2];
            String matrixFile = args[3];
            String path = args[5];
            Fasta fasta = new Fasta(k, seq);
            fasta.setMatrix(matrixFile);
            fasta.runDiagonals(query, n, path);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Usage: java -jar blatt4_1.jar k dbseq0,...,dbseqn query matrixfile n output");
        }
    }
}
