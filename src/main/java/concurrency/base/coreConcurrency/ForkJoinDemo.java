package concurrency.base.coreConcurrency;

/**
 * 分工
 */
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


/**
 * Fork/Join 分工模式是 Java 并发编程中一种适用于大规模计算密集型任务的分工模式。下面是一个计算斐波那契数列的示例程序，使用了 Fork/Join 分工模式来提高计算效率：
 *
 * 在这个示例程序中，我们创建了一个 ForkJoinPool 对象，并创建了一个 FibonacciTask 任务，
 * 将其提交给线程池处理。在 FibonacciTask 的 compute() 方法中，如果 n 小于等于 1，则直接返回 n，否则，创建两个新的 FibonacciTask 任务，
 * 并使用 fork() 方法将它们提交给 ForkJoinPool 处理。最后，使用 join() 方法等待两个子任务的结果，并将它们相加作为当前任务的结果。
 *
 * 通过使用 Fork/Join 分工模式，将一个大任务拆分成多个小任务，可以充分利用多核处理器的性能优势，提高计算效率。
 * 在这个示例程序中，我们将计算斐波那契数列的任务拆分成多个小任务，最终实现了并行计算的效果。
 *
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        // 创建一个 Fork/Join 线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 创建一个任务
        FibonacciTask task = new FibonacciTask(20);

        // 提交任务
        int result = forkJoinPool.invoke(task);

        // 输出结果
        System.out.println("第 20 个斐波那契数列的值为：" + result);
    }
}

class FibonacciTask extends RecursiveTask<Integer> {
    private int n;

    public FibonacciTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        } else {
            FibonacciTask task1 = new FibonacciTask(n - 1);
            task1.fork();
            FibonacciTask task2 = new FibonacciTask(n - 2);
            task2.fork();
            return task1.join() + task2.join();
        }
    }
}

