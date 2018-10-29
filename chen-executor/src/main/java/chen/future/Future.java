package chen.future;

public interface Future<T> {
    //返回计算后的结果，该⽅法会陷⼊阻塞状态
    T get() throws InterruptedException;
    //判断任务是否已经被执⾏完成
    boolean done();
}
