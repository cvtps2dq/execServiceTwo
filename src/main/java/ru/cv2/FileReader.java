package ru.cv2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class FileReader {
    public long read(String fileName){
        try(Stream<String> stream = Files.lines(Paths.get(fileName))){
            return stream.mapToLong(Long::valueOf).sum();
        }
        catch (IOException ex){
            System.out.println("Failed to read file " + "'" + fileName + "'" + " !");
            ex.printStackTrace();
        }
        return -1;
    }

    public void execute(String initialFolder, int fileCount){
        long start = System.nanoTime();
        long sum = 0L;
        System.out.println("Non-thread read. All files in order.");

        //read all files one by one without threading.

        for (int i = 0; i < fileCount; i++){
            FileReader reader = new FileReader();
            sum += reader.read(initialFolder + "/" + i + ".txt");
            System.out.println("File " + i + " done reading.");
        }
        long end = System.nanoTime();
        System.out.println("Non-thread read executed: " + sum + ". Completed in: "
                + (end - start) / 10_000 + " ms.");
    }

    public void executeThreaded(String initialFolder, int fileCount, int threadCount){

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
