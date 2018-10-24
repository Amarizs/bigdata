package chen.threadPool;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue{

    //任务队列最大容量
    private final int limit;

    //若队列任务已满，则需执行拒绝策略
    private final DenyPolicy denyPolicy;

    //线程池
    private final ThreadPool threadPool;

    //存放任务的队列
    private final LinkedList<Runnable> runnableList = new LinkedList<>();

    public LinkedRunnableQueue(int limit,DenyPolicy denyPolicy,ThreadPool threadPool){
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }
    @Override
    public void offer(Runnable runnable) {
        synchronized (runnableList) {
            if(runnableList.size() >= limit){
                //队列无法容纳新任务时执行拒绝策略
                denyPolicy.reject(runnable,threadPool);
            } else {
                //将任务添加到队列末尾，并唤醒阻塞的线程
                runnableList.addLast(runnable);
                runnableList.notifyAll();
            }
        }
    }

    @Override
    public Runnable take() {
        synchronized (runnableList) {
            while (runnableList.isEmpty()) {
                try {
                    //如果当前队列中没有可执行的任务，当前线程会挂起
                    runnableList.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //从任务队列头部移除一个任务并返回
            return runnableList.removeFirst();
        }
    }

    @Override
    public int size() {
        synchronized (runnableList) {
            //返回当前队列中的任务数
            return runnableList.size();
        }
    }
}
