package concurrency.base.concurrencySources;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发可见性问题：解决方案volatile
 * 将 count 变量声明为 volatile，这样就能保证该变量对于所有线程都是可见的。即使一个线程修改了 count 的值，其他线程也能够立即感知到。
 *
 * 虽然这段代码解决了并发的可见性问题，但并发的原子性问题：count += 1实际上是三个操作的组合，即读取count值、将count值加1、将加1后的值写回count变量。在多线程并发的情况下，可能会发生多个线程同时读取count的值为x，
 * 然后将其加1后写回count变量，这样就会导致多个线程只加了1而不是2，从而导致计数器count的值小于期望的200000，却没有得到解决；解决并发的原子性问题，需要使用。
 */
public class VisibilitySolve_01 {
    private volatile long count = 0;

    private void add10K() {
        int idx = 0;
        while (idx++ < 100000) {
            count += 1;
        }
    }

    public static long calc() throws InterruptedException {
        final VisibilitySolve_01 test = new VisibilitySolve_01();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            test.add10K();
        });
        executor.submit(() -> {
            test.add10K();
        });
        executor.shutdown();
        while (!executor.isTerminated()) {
            //等待线程池中的所有任务完成
        }
        return test.count;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(VisibilitySolve_01.calc());
    }
}
