package concurrency.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();

        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 线程 A 等待
        executorService.execute(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 开始等待");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 被唤醒了");
            }
        });

        // 线程 B 唤醒
        executorService.execute(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 开始唤醒");
                lock.notifyAll();
                System.out.println(Thread.currentThread().getName() + " 唤醒完成");
            }
        });

        // 关闭线程池
        executorService.shutdown();
    }
}

