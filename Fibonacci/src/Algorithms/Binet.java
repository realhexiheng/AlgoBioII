package Algorithms;

/**
 * @Author Xiheng He
 * @ClassName Binet
 * @Description TODO
 * @Date 2021/11/2021/11/1/2:17
 * @Version 1.0
 */
public class Binet implements Fibonacci{

    public int fib(int n) {
        double sqrt = Math.sqrt(5);
        double phi=(1 + sqrt) / 2;
        return (int) Math.round((Math.pow(phi, n) / sqrt));
    }
}
