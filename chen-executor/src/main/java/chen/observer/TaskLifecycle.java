package chen.observer;

public interface TaskLifecycle<T> {

    //任务启动时会触发onStart⽅法
    void onStart(Thread thread);

    //任务正在运⾏时会触发onRunning⽅法
    void onRunning(Thread thread);

    //任务运⾏结束时会触发onFinish⽅法，其中result是任务执⾏结束后的结果
    void onFinish(Thread thread, T result);

    //任务执⾏报错时会触发onError⽅法
    void onError(Thread thread, Exception e);

    //⽣命周期接⼝的空实现（Adapter）
    class EmptyLifecycle<T> implements TaskLifecycle<T> {
        @Override
        public void onStart(Thread thread) {
            //do nothing
        }

        @Override
        public void onRunning(Thread thread) {
            //do nothing
        }

        @Override
        public void onFinish(Thread thread, T result) {
            //do nothing
        }

        @Override
        public void onError(Thread thread, Exception e) {
            //do nothing
        }
    }
}
