package chen.future;

public class FutureTask<T> implements Future<T> {
    //计算结果
    private T result;
    //任务是否完成
    private boolean isDone = false;
    //定义对象锁
    private final Object LOCK = new Object();

    @Override
    public T get() throws InterruptedException {
        synchronized (LOCK) {
            //当任务还没完成时，调⽤get⽅法会被挂起⽽进⼊阻塞
            while (!isDone) {
                LOCK.wait();
            }
            //返回最终计算结果
            return result;
        }
    }

    //finish⽅法主要⽤于为FutureTask设置计算结果
    protected void finish(T result) {
        synchronized (LOCK) {
//balking设计模式
            if (isDone)
                return;
//计算完成，为result指定结果，并且将isDone设为true，同时唤醒阻塞中的线程
            this.result = result;
            this.isDone = true;
            LOCK.notifyAll();
        }
    }

    //返回当前任务是否已经完成
    @Override
    public boolean done() {
        return isDone;
    }
}