package chen.future;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class FutureTest {

    @Test
    public void test1() {
        //定义不需要返回值的FutureService
        FutureService<Void, Void> service = FutureService.newService();
        //submit⽅法为⽴即返回的⽅法
        Future<?> future = service.submit(() ->
        {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I am finish done.");
        });
        //get⽅法会使当前线程进⼊阻塞
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        //定义有返回值的FutureService
        FutureService<String, Integer> service = FutureService.newService();
        //submit⽅法会⽴即返回
        Future<Integer> future = service.submit(input ->
        {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return input.length();
        }, "Hello");
        //get⽅法使当前线程进⼊阻塞，最终会返回计算的结果
        try {
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        FutureService<String, Integer> service = FutureService.newService();
        service.submit(input ->
        {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return input.length();
        }, "Hello", System.out::println);
    }
}
