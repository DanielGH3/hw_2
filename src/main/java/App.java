import java.util.concurrent.Callable;

public class App {
    public static void main(String[] args) {
        Ex2_1 ex2 = new Ex2_1();

        String[] paths = Ex2_1.createTextFiles(5, 0, 10);

        int ln1 = Ex2_1.getNumOfLines(paths);
        System.out.println(ln1);

        int ln2 = ex2.getNumOfLinesThreads(paths); 
        System.out.println(ln2);

        int ln3 = ex2.getNumOfLinesThreadPool(paths);
        System.out.println(ln3);

        CustomExecutor exx = new CustomExecutor();

        var t1 =  Task.createTask(
            ()->{try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } System.out.println("done"); return 1;},
            TaskType.OTHER
        );

        var t2 =  Task.createTask(
            ()->{System.out.println("hello2"); return 1;},
            TaskType.OTHER
        );

        var t3 =  Task.createTask(
            ()->{System.out.println("hello1"); return 1;},
            TaskType.COMPUTATIONAL
        );    

        var c = new Callable<Integer>() {
            @Override
            public Integer call(){
                System.out.println("hello1.1");
                return 0;
            }
        };

        exx.submit(t1);
        exx.submit(c);
        exx.submit(t2);
        exx.submit(t3);

        exx.gracefullyTerminate();
    }
}
