package Algorithms;

/**
 * @Author Xiheng He
 * @ClassName IterativeAlgo
 * @Description TODO
 * @Date 2021/10/2021/10/31/15:52
 * @Version 1.0
 */
public class IterativeAlgo implements Fibonacci{

    @Override
    public int fib(int n) {
        if (n == 1 || n == 2) return 1;
        int a = 0,v = 1, l = 1;
        for (int i = 3; i <= n; i++) {
            a = v + l;
            v = l;
            l = a;
        }
        return a;
    }
}

