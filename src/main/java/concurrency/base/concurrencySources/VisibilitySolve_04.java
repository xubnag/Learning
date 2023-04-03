package concurrency.base.concurrencySources;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 并发可见性问题：解决方案Lock
 * 使用Lock同样可以保证同一时刻只有一个线程可以执行add10K方法。此处使用ReentrantLock实现可重入锁，可以保证同一线程多次获得锁时不会出现死锁的情况。
 * 和使用synchronized一样，使用Lock也可以解决可见性问题。
 */
public class VisibilitySolve_04 {
    private long count = 0;
    private final Lock lock = new ReentrantLock();

    private void add10K() {
        lock.lock();
        try {
            int idx = 0;
            while (idx++ < 900000) {
                count += 1;
            }
        } finally {
            lock.unlock();
        }
    }

    public static long calc() throws InterruptedException {
        final VisibilitySolve_04 test = new VisibilitySolve_04();
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
        System.out.println(VisibilitySolve_04.calc());
    }
}
