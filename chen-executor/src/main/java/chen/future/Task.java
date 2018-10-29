package chen.future;

@FunctionalInterface
public interface Task<IN, OUT>
{
    //给定⼀个参数，经过计算返回结果
    OUT get(IN input);
}