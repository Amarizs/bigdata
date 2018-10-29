package chen.observer;

public class ObservableThread<T> extends Thread implements Observable {

    private final TaskLifecycle<T> lifecycle;
    private final Task<T> task;
    private Cycle cycle;

    public ObservableThread(Task<T> task) {
        this(new TaskLifecycle.EmptyLifecycle<>(), task);
    }

    //指定TaskLifecycle的同时指定Task
    public ObservableThread(TaskLifecycle<T> lifecycle, Task<T> task) {
        super();
        //Task不允许为null
        if (task == null)
            throw new IllegalArgumentException("The task is required.");
        this.lifecycle = lifecycle;
        this.task = task;
    }

    @Override
    public final void run() {
        //在执⾏线程逻辑单元的时候，分别触发相应的事件
        this.update(Cycle.STARTED, null, null);
        try {
            this.update(Cycle.RUNNING, null, null);
            T result = this.task.call();
            this.update(Cycle.DONE, result, null);
        } catch (Exception e) {
            this.update(Cycle.ERROR, null, e);
        }
    }

    private void update(Cycle cycle, T result, Exception e) {
        this.cycle = cycle;
        if (lifecycle == null)
            return;
        try {
            switch (cycle) {
                case STARTED:
                    this.lifecycle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.lifecycle.onRunning(currentThread());
                    break;
                case DONE:
                    this.lifecycle.onFinish(currentThread(), result);
                    break;
                case ERROR:
                    this.lifecycle.onError(currentThread(), e);
                    break;
            }
        } catch (Exception ex) {
            if (cycle == Cycle.ERROR) {
                throw ex;
            }
        }
    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }
}
