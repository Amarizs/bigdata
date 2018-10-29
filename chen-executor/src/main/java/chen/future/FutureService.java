package chen.future;

public interface FutureService<IN,OUT> {
    //提交不需要返回值的任务，Future.get⽅法返回的将会是null
    Future<?> submit(Runnable runnable);
    //提交需要返回值的任务，其中Task接⼝代替了Runnable接⼝
    Future<OUT> submit(Task<IN, OUT> task, IN input);
    //使⽤静态⽅法创建⼀个FutureService的实现
    static <T, R> FutureService<T, R> newService()
    {
        return new FutureServiceImpl<>();
    }
    //增加回调接⼝Callback，当任务执⾏结束之后，Callback会得到执⾏
    Future<OUT> submit(Task<IN, OUT> task, IN input, Callback<OUT> callback);
}
