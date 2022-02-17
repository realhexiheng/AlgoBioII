import Algorithms.Binet;
import Algorithms.IterativeAlgo;
import Algorithms.LogarithmicAlgo;
import Algorithms.RecursiveAlgo;

import java.util.Scanner;

/**
 * @Author Xiheng He
 * @ClassName FibonacciRunner
 * @Description TODO
 * @Date 2021/10/2021/10/31/15:44
 * @Version 1.0
 */
public class FibonacciRunner {

    public static void main(String[] args) {
        System.out.print("Give your fibonacci number : ");
        Scanner in = new Scanner(System.in);

        IterativeAlgo iterativeAlgo = new IterativeAlgo();
        RecursiveAlgo recursiveAlgo = new RecursiveAlgo();
        LogarithmicAlgo logarithmicAlgo = new LogarithmicAlgo();
        Binet binet = new Binet();

        int n = in.nextInt();

        System.out.println("iterative approach : " + iterativeAlgo.fib(n));
        System.out.println("recursive approach : " + recursiveAlgo.fib(n));
        System.out.println("logarithmic approach : " + logarithmicAlgo.fib(n));
        System.out.println("Binet's formula : " + binet.fib(n));
    }
}
