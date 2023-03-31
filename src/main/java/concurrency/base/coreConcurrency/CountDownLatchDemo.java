package concurrency.base.coreConcurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 下面是一个经典的 CountDownLatch Demo，它模拟了多个线程并发执行，但需要等待某些线程完成后才能继续执行。
 * 在该 Demo 中，我们创建了 3 个线程分别执行不同的任务，这些任务的执行时间是随机的。
 * 同时，我们创建了一个 CountDownLatch，它的初始计数为 3，表示需要等待 3 个线程都执行完任务后才能继续执行主线程。
 *
 *
 * 在上面的代码中，我们创建了一个 CountDownLatch 对象 latch，它的计数器初始值为 3。我们创建了 3 个 Worker 线程对象 w1、w2、w3，
 * 并将它们的 CountDownLatch 对象设置为 latch。在每个 Worker 线程的 run 方法中，先随机生成一个执行时间，然后模拟执行这个任务，
 * 最后调用 CountDownLatch 的 countDown 方法表示完成了一个任务。在主线程中，我们调用 CountDownLatch 的 await 方法等待所有 Worker 线程完成任务后才能继续执行主线程。
 *
 * 可以看到，虽然三个线程是同时启动的，但由于任务执行时间不同，它们的结束时间也不同。但是，
 * 主线程会等待所有的线程都执行完毕后才会输出 "All workers have finished their tasks."。
 *
 *
 * 这个示例说明了 CountDownLatch 的应用场景：主线程需要等待多个线程完成任务后才能继续执行。而 CountDownLatch 的计数器是可变的，
 * 可以在多个线程中通过 countDown 方法来减少计数器的值，一旦计数器的值变为 0，
 *
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        Worker w1 = new Worker(latch, "Worker1");
        Worker w2 = new Worker(latch, "Worker2");
        Worker w3 = new Worker(latch, "Worker3");
        w1.start();
        w2.start();
        w3.start();
        latch.await();
        System.out.println("All workers have finished their tasks.");
    }

    private static class Worker extends Thread {
        private CountDownLatch latch;
        private String name;
        private Random random;

        public Worker(CountDownLatch latch, String name) {
            this.latch = latch;
            this.name = name;
            this.random = new Random();
        }

        public void run() {
            try {
                int workTime = random.nextInt(1000);
                System.out.println(name + " is working for " + workTime + "ms.");
                Thread.sleep(workTime);
                System.out.println(name + " has finished the task.");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

