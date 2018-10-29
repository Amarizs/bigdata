package chen.future;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * FutureServiceImpl的主要作⽤在于当提交任务时创建⼀个新的线程来受理该任务,进⽽达到任务异步执⾏的效果
 */
public class FutureServiceImpl<IN, OUT> implements FutureService<IN, OUT> {
    //为执⾏的线程指定名字前缀（再三强调，为线程起⼀个特殊的名字是⼀个⾮常好的编程习惯）
    private final static String FUTURE_THREAD_PREFIX = "FUTURE-";
    private final AtomicInteger nextCounter = new AtomicInteger(0);

    private String getNextName() {
        return FUTURE_THREAD_PREFIX + nextCounter.getAndIncrement();
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        final FutureTask<Void> future = new FutureTask<>();
        new Thread(() ->
        {
            runnable.run();
            //任务执⾏结束之后将null作为结果传给future
            future.finish(null);
        }, getNextName()).start();
        return future;
    }

    @Override
    public Future<OUT> submit(Task<IN, OUT> task, IN input) {
        final FutureTask<OUT> future = new FutureTask<>();
        new Thread(() ->
        {
            OUT result = task.get(input);
            //任务执⾏结束之后，将真实的结果通过finish⽅法传递给future
            future.finish(result);
        }, getNextName()).start();
        return future;
    }

    //增加回调接⼝Callback，当任务执⾏结束之后，Callback会得到执⾏
    @Override
    public Future<OUT> submit(Task<IN, OUT> task, IN input, Callback<OUT> callback)
    {
        final FutureTask<OUT> future = new FutureTask<>();
        new Thread(() ->
        {
            OUT result = task.get(input);
            future.finish(result);
            //执⾏回调接⼝
            if (null != callback)
                callback.call(result);
        }, getNextName()).start();
        return future;
    }
}