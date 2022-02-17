package Algorithms;

/**
 * @Author Xiheng He
 * @ClassName RecursiveAlgo
 * @Description TODO
 * @Date 2021/10/2021/10/31/15:53
 * @Version 1.0
 */
public class RecursiveAlgo implements Fibonacci{


    public int fib(int n) {
        if (n == 1 || n == 2)
            return 1;
        else if (n == 0)
            return 0;
        else
            return fib(n - 1) + fib(n - 2);
    }
}
