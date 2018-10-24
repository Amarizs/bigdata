package chen.threadPool;

public interface ThreadPool {
    //提交任务到线程池
    void execute(Runnable runnable);
    //关闭线程池
    void shutdown();
    //获取线程池初始化大小
    int getInitSize();
    //获取线程池最大的线程数
    int getMaxSize();
    //获取线程池核心线程数量
    int getCoreSize();
    //获取线程池中用于缓存任务队列的大小
    int getQueueSize();
    //获取线程池中活跃的线程数量
    int getActiveCount();
    //查看线程池是否已被关闭
    boolean isShutDown();
}
