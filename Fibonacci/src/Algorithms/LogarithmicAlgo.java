package Algorithms;

/**
 * @Author Xiheng He
 * @ClassName LogarithmicAlgo
 * @Description TODO
 * @Date 2021/10/2021/10/31/15:55
 * @Version 1.0
 */
public class LogarithmicAlgo implements Fibonacci{

    @Override
    public int fib(int n) {
        Integer[] F = new Integer[n + 1];
        return log(n, F);
    }

    public int log(int n, Integer[] F) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        int k = (int) Math.floor(n / 2);
        F[k] = log(k, F);
        if (n % 2 == 0) {
            if (F[k - 1] == null && k - 1 != 0)
                F[k - 1] = log(k - 1, F);
            if (F[k + 1] == null)
                F[k + 1] = log(k + 1, F);
            F[n] = F[k] * F[k + 1] + F[k] * F[k - 1];
        } else {
            if (F[k + 1] == null)
                F[k + 1] = log(k + 1, F);
            F[n] = F[k + 1] * F[k + 1] + F[k] * F[k];
        }
        return F[n];
    }
}
