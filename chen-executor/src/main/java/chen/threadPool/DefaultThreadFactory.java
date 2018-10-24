package chen.threadPool;

public class DefaultThreadFactory implements ThreadFactory{
    @Override
    public Thread createThread(Runnable runnable) {
        return new Thread(runnable);
    }
}
