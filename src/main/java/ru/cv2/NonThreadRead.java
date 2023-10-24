package ru.cv2;

public class NonThreadRead {
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
}
