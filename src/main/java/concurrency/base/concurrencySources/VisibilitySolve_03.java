package concurrency.base.concurrencySources;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发可见性问题：解决方案synchronized
 * 使用synchronized关键字可以保证方法同步，即同一时刻只有一个线程可以执行add10K方法。这样就解决了可见性问题，保证了多线程环境下对count的访问是同步的。
 */
public class VisibilitySolve_03 {
    private long count = 0;

    private synchronized void add10K() {
        int idx = 0;
        while (idx++ < 100000) {
            count += 1;
        }
    }

    public static long calc() throws InterruptedException {
        final VisibilitySolve_03 test = new VisibilitySolve_03();
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
