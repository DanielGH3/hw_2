import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Task<T> extends FutureTask<T> implements Comparable<Task<?>>{
    TaskType type;

    public Task(Callable<T> action){
        super(action);
        type = TaskType.OTHER;
    }

    public Task(Callable<T> action, TaskType type){
        super(action);
        this.type = type;
    }

    @Override
    public void run() {
        super.run();
    }

    public int getPriorityValue(){
        return type.getPriorityValue();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return super.get();
    }

    @Override
    public int compareTo(Task<?> o) {
        return Integer.compare(type.getPriorityValue(), o.getPriorityValue());
    }   

    public static <T> Task<T> createTask(Callable<T> action){
        return new Task<T>(action);
    }

    public static <T> Task<T> createTask(Callable<T> action, TaskType type){
        return new Task<T>(action, type);
    }
}
