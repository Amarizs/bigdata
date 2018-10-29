package chen.observer;

@FunctionalInterface
public interface Task<T> {
    //任务执⾏接⼝，该接⼝允许有返回值
    T call();
}