import java.util.concurrent.*;

class HelloThreading{
    public static void main (String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        int[] largeArray = new int[100000];
        
        for (int i = 0; i < largeArray.length; i++){
            largeArray[i] = (int) ((Math.random() + 1)*100);
        }

        Callable<Integer> task1 = () -> processSegment(0, 25000);
        Callable<Integer> task2 = () -> processSegment(25000, 50000);
        Callable<Integer> task3 = () -> processSegment(50000, 75000);
        Callable<Integer> task4 = () -> processSegment(75000, 100000);

        Future<Integer> f1 = executor.submit(task1);
        Future<Integer> f2 = executor.submit(task2);
        Future<Integer> f3 = executor.submit(task3);
        Future<Integer> f4 = executor.submit(task4);

        int password = 0;
        if (f1.get() > -1){
            password = f1.get();
        }
        if (f2.get() > -1){
            password = f2.get();
        }
        if (f3.get() > -1){
            password = f3.get();
        }
        if (f4.get() > -1){
            password = f4.get();            
        }
        executor.shutdown();
        System.out.println("Password: " + password);
    }

    private static int processSegment(int start, int end) {

        for (int i = start; i < end; i++) {
            if (checkPassword(i)){
                return i;
            }
        }
        return -1;
    }

    private static boolean checkPassword(int inputted){
        return inputted == 31415;
    }
    
}