package concurrency.base.concurrencySources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 并发可见性问题：解决方案AtomicLong
 * 使用 AtomicLong 类可以保证 count 变量的原子性和可见性。它提供了一些原子性的操作，如 getAndAdd、getAndIncrement 等，
 * 保证对于这些操作的执行是原子性的，并且其他线程能够立即感知到 count 变量的值的更新。
 *
 * 下方的代码中，count变量被声明为AtomicLong类型，而add10K()方法中的count += 1则被替换为count.incrementAndGet()，
 * 该方法会以原子方式将当前值加1并返回新值。这样就避免了多个线程同时修改count变量的问题，从而保证了计数器的正确性。
 */
public class VisibilitySolve_02 {
    private AtomicLong count = new AtomicLong(0);

    private void add10K() {
        int idx = 0;
        while (idx++ < 100000) {
            count.getAndAdd(1);
        }
    }

    public static long calc() throws InterruptedException {
        final VisibilitySolve_02 test = new VisibilitySolve_02();
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
        return test.count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(VisibilitySolve_02.calc());
    }
}
