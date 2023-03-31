package concurrency.base.coreConcurrency;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 下面是一个使用Java可重入锁（ReentrantLock）实现的Demo，其中展示了如何使用可重入锁来实现互斥访问共享资源：
 *
 * 在这个Demo中，我们使用了一个ReentrantLock来保护count变量。在每个线程中，当线程需要修改count变量时，它首先必须获取锁，然后执行修改操作，最后释放锁。
 * 这样，只有一个线程能够持有锁，从而保证了对count变量的互斥访问。最终，我们通过输出count的值来验证这个Demo的正确性。
 *
 * 需要注意的是，在使用可重入锁时，需要确保每个线程都在获取锁之后，最终都会释放锁，否则可能会出现死锁的情况。这也是为什么在上述代码中，我们使用了try-finally语句来确保锁的释放。
 *
 */
public class ReentrantLockDemo {
    private static ReentrantLock lock = new ReentrantLock();
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    lock.lock();
                    try {
                        count++;
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    lock.lock();
                    try {
                        count--;
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Final count is: " + count);
    }
}

