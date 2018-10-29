package chen.future;

@FunctionalInterface
public interface Callback<T>
{
    //任务完成后会调⽤该⽅法，其中T为任务执⾏后的结果
    void call(T t);
}