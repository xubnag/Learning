package concurrency.base.concurrencySources;


import java.util.concurrent.*;

/**
 * 使用线程池创建100个线程获取Singleton实例，然后将结果保存到数组中。由于线程池的执行顺序是不确定的，因此可能会存在多个线程同时执行synchronized块的情况，
 * 这会导致在某些时刻instance被重复创建，从而出现并发有序性问题。最后，检查是否存在多个实例对象即可验证这个问题。
 */
public class Orderliness_02 {
    private static volatile Orderliness_02 instance;
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    private Orderliness_02() {}

    public static Orderliness_02 getInstance() {
        if (instance == null) {
            synchronized(Orderliness_02.class) {
                // 双重检查锁定，确保instance只被创建一次
                if (instance == null) {
                    Future<Orderliness_02> future = executorService.submit(() -> new Orderliness_02());
                    try {
                        instance = future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        // 创建10个线程获取Singleton实例，并将结果保存到数组中
        Orderliness_02[] singletons = new Orderliness_02[100];
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(() -> singletons[index] = Orderliness_02.getInstance());
        }

        // 等待所有线程执行完成
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 检查是否存在多个实例对象
        boolean isMultipleInstanceExist = false;
        for (int i = 1; i < 100; i++) {
            if (singletons[i] != singletons[i-1]) {
                isMultipleInstanceExist = true;
                break;
            }
        }

        System.out.println("是否存在多个实例对象：" + isMultipleInstanceExist);
    }
}
