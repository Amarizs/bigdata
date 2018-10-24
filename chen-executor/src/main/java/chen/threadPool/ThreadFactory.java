package chen.threadPool;

@FunctionalInterface
public interface ThreadFactory {
    Thread createThread(Runnable runnable);
}
