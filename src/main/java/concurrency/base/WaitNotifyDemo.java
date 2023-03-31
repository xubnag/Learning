package concurrency.base;

/**
 * 互斥机制：synchronized、wait()、notify()
 */
public class WaitNotifyDemo {
    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();

        // 线程 A 等待
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 开始等待");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 被唤醒了");
            }
        }, "线程 A");

        // 线程 B 唤醒
        Thread threadB = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 开始唤醒");
                lock.notify();
                System.out.println(Thread.currentThread().getName() + " 唤醒完成");
            }
        }, "线程 B");

        threadA.start();
        Thread.sleep(1000); // 等待线程 A 开始等待
        threadB.start();
    }
}
