package chen.threadPool;

public interface RunnableQueue {
    //把新添加的任务提交到队列中
    void offer(Runnable runnable);
    //工作线程从队列中获取Runnable
    Runnable take();
    //获取任务队列中任务的数量
    int size();
}
