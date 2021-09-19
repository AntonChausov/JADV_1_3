import java.util.concurrent.RecursiveTask;

public class Summator extends RecursiveTask<Integer> {
    int[] array;
    int start;
    int end;

    public Summator(int start, int end, int[] array) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        final int diff = end - start;
        switch (diff){
            case 0:
                return 0;
            case 1:
                return array[start];
            case 2:
                return array[start] + array[start + 1];
            default:
                return forkTasksAndGetResult();
        }
    }
    private int forkTasksAndGetResult() {
        final int middle =(end - start)/2+ start;
        Summator task1 = new Summator(start, middle, array);
        Summator task2 =new Summator(middle, end, array);
        invokeAll(task1, task2);
        return task1.join()+ task2.join();
    }
}
