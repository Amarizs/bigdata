package chen.threadPool;

public class RunnableDenyException extends RuntimeException {
    public RunnableDenyException(String msg){
        super(msg);
    }
}
