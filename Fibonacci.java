import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Fibonacci {
    public static void main(String[] args) {
//        System.out.println(fibonacci(40));
        Random random = new Random(567);
        System.out.println(random.nextLong());
        List<Integer> list = new ArrayList<>();

    }

    public static int fibonacci(int n) {
        if (n <= 1)
            return n;
        else
            return fibonacci(n - 1) + fibonacci(n - 2);
    }
}