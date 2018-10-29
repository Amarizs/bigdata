package chen.observer;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ObserverTest {

    @Test
    public void test1(){
        Observable observableThread = new ObservableThread<>(() ->
        {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" finished done.");
            return null;
        });
        observableThread.start();
    }

    @Test
    public void test2(){
        final TaskLifecycle<String> lifecycle = new TaskLifecycle.EmptyLifecycle<String>() {
            public void onFinish(Thread thread, String result) {
                System.out.println("The result is " + result);
            }
        };
        Observable observableThread = new ObservableThread<>(lifecycle, () ->
        {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" finished done.");
            return "Hello Observer";
        });
        observableThread.start();
    }
}
