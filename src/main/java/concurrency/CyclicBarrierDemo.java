package concurrency;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    private static final int NUM_RUNNERS = 5; // 参赛选手数量
    private static final CyclicBarrier START_GATE = new CyclicBarrier(NUM_RUNNERS + 1); // 使用CyclicBarrier等待所有选手准备完毕

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < NUM_RUNNERS; i++) {
            Thread runner = new Thread(new Runner("Runner " + (i + 1)));
            runner.start();
        }
        System.out.println("Ready...");
        Thread.sleep(1000);
        System.out.println("Set...");
        Thread.sleep(1000);
        System.out.println("Go!");
        try {
            START_GATE.await(); // 所有选手准备完毕后开始比赛
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("All runners started to race.");
    }

    private static class Runner implements Runnable {
        private final String name;
        private final Random rand = new Random();

        public Runner(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " is ready.");
                START_GATE.await(); // 等待所有选手准备完毕
                System.out.println(name + " starts to race.");
                Thread.sleep(rand.nextInt(3000)); // 随机模拟选手比赛用时
                System.out.println(name + " finishes the race.");
                START_GATE.await(); // 等待所有选手完成比赛
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}

