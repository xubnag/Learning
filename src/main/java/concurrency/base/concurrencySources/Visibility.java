package concurrency.base.concurrencySources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 并发的可见性问题：
 *
 * 具体来说，当多个线程同时访问共享变量时，由于线程间的执行时机是不确定的，可能会出现线程 A 修改了变量的值，但是线程 B 没有及时看到变量的最新值的情况。这就会导致线程之间的数据不一致，出现并发问题。
 *
 * 在示例代码中，由于两个线程同时对 count 变量进行修改，且对于 count 变量的修改操作不是原子操作，因此可能会出现并发问题。例如，线程 A 执行 count += 1 操作，
 * 先将 count 变量的值从 0 修改为 1，但是这个修改可能还没有被写回主存，此时线程 B 也执行 count += 1 操作，此时线程 B 看到的 count 变量的值仍然是 0，因此线程 B 将 count 变量的值修改为 1，
 * 然后将修改后的值写回主存。此时，线程 A 再将 count 变量的值写回主存，由于之前的修改被覆盖，因此线程 A 修改的值 1 就被丢失了，最终 count 变量的值只会是 1，而不是预期的 2。
 *
 * 为了避免这种并发问题，可以使用 synchronized 关键字或 ReentrantLock 来保证多个线程对共享变量的访问具有互斥性，也可以使用 volatile 关键字来保证多个线程对共享变量的修改具有可见性。
 *
 *
 */
public class Visibility {
    private long count = 0;

    private void add10K() {
        int idx = 0;
        while (idx++ < 100000) {
            count += 1;
        }
    }

    public static long calc() throws InterruptedException {
        final Visibility test = new Visibility();
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
        System.out.println(VisibilitySolve_03.calc());
    }
}
