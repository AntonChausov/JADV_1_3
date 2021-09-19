import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class Main {

    static final int MAX_VALUE = 10;

    public static void main(String[] args) {
        int length = 40_000_000;
        int[] array = generateArray(length);
        long start = System.currentTimeMillis();
        long sum = summArray(array);
        long end = System.currentTimeMillis();
        long oneThreadTime = end - start;
        System.out.println("Однопоточный результат: " + sum);
        System.out.println("Однопоточное время: " + oneThreadTime);

        start = System.currentTimeMillis();
        final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        final ForkJoinTask<Integer> result = forkJoinPool.submit(new Summator(0, array.length, array));
        end = System.currentTimeMillis();
        long manyThreadTime = end - start;
        System.out.println("Многопоточный результат: " + result.join());
        System.out.println("Многопоточное время: " + manyThreadTime);
    }

    private static long summArray(int[] array) {
        long result = 0;
        for (int elem : array) {
            result += elem;
        }
        return result;
    }

    static int[] generateArray(int length) {
        int[] result = new int[length];
        Random random = new Random();
        for (int i = 0; i < result.length; i++) {
            result[i] = random.nextInt(MAX_VALUE);
        }
        return result;
    }
}
