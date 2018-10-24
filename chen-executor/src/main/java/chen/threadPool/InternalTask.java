package chen.threadPool;

public class InternalTask implements Runnable{

    private final RunnableQueue runnableQueue;
    private volatile boolean running = true;

    public InternalTask(RunnableQueue runnableQueue){
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        //如果当前任务为running并且没有被中断，则不断从队列中取出任务并执行
        while(running && !Thread.currentThread().isInterrupted()){
            try{
                Runnable task = runnableQueue.take();
                task.run();
            } catch (Exception e) {
                running = false;
                break;
            }
        }
    }
    public void stop(){
        this.running = false;
    }
}
