package ru.cv2;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadRead {

    public void execute(String initialFolder, int fileCount, int threadCount){

        long start = System.nanoTime();
        System.out.println("Threaded read starts.");

        // set up ExecutorService and create set of Future longs to collect data
        // from threads and safely sum it when threads terminates

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        Set<Future<Long>> sums = new HashSet<>();

        // creating tasks in for loop
        for(int i = 0; i < fileCount; i++){
            Future<Long> future = executorService.submit(new FileReadTask(initialFolder + "/" + i + ".txt"));
            System.out.println("File " + i + " was put into the futures set.");
            sums.add(future);
        }

        Long sum = 0L;
        // forEach future in sum set we sum all data safely
        for (Future<Long> future : sums){
            try {
                sum += future.get();
            }
            // bad behaviour
            catch (InterruptedException | ExecutionException ex){
                throw new RuntimeException(ex);
            }
        }

        // sleep well, executorService, you served me well.
        executorService.shutdown();

        // stats
        long end = System.nanoTime();
        System.out.println("Threaded read executed. Result: " + sum + ". Completed in: " +
                (end - start) / 10_000 + "ms.");
    }
    }
