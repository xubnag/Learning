package concurrency.base.concurrencySources;

/**
 * 利用双重检查 创建单例对象（多线程并发执行情况下，会存在并发有序性问题）
 * Singleton实现是线程安全的，并且能够保证只有一个实例被创建。在getInstance方法中，我们首先判断当前实例对象是否已经被初始化，
 * 如果没有，则使用synchronized关键字对类加锁，确保只有一个线程可以进入临界区创建实例。同时为了增强性能，我们在内层if语句中再次判断变量是否为null，这样可以避免不必要的同步开销。
 */
public class Orderliness {
    private static Orderliness instance;

    private Orderliness() {
    }

    public static Orderliness getInstance() {
        if (instance == null) {
            synchronized (Orderliness.class) {
                if (instance == null) {
                    instance = new Orderliness();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Orderliness singleton1 = Orderliness.getInstance();
        Orderliness singleton2 = Orderliness.getInstance();

        System.out.println(singleton1 == singleton2); // 输出true，说明只创建了一个实例对象
    }
}
