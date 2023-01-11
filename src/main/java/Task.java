import java.util.concurrent.Callable;

public class Task<T> implements Runnable, Callable<T>, Comparable<Task<?>>{
    Callable<T> action;
    TaskType type;

    public Task(Callable<T> action){
        this.action = action;
        type = TaskType.OTHER;
    }

    public Task(Callable<T> action, TaskType type){
        this.action = action;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            this.call();
        } catch (Exception e) {
            System.out.println("Task runtine error.");
        }
    }

    @Override
    public T call() throws Exception {
        return action.call();
    }

    public int getPriorityValue(){
        return type.getPriorityValue();
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
