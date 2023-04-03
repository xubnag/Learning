package concurrency.base.concurrencySources;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Orderliness_01 {
    private static  Orderliness_01 instance;

    private Orderliness_01() {
    }

    public static Orderliness_01 getInstance() {
        if (instance == null) {
            synchronized (Orderliness_01.class) {
                if (instance == null) {
                    instance = new Orderliness_01();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            Orderliness_01 singleton1 = Orderliness_01.getInstance();
            System.out.println(Thread.currentThread().getName() + " : " + singleton1);
        });

        executorService.submit(() -> {
            Orderliness_01 singleton2 = Orderliness_01.getInstance();
            System.out.println(Thread.currentThread().getName() + " : " + singleton2);
        });

        executorService.shutdown();
    }
}
