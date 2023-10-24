package ru.cv2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileGenerator {
    public void generateOne(String fileName, int length, int boundary){
        try{

            //prepare writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            Random random = new Random();

            //generate numbers and write to file
            for(int i = 0; i < length; i++){
                writer.write(Integer.toString(random.nextInt(boundary)));
                writer.newLine();
            }

            //attempt to close file writing routine
            writer.close();
        }

        //bad behaviour
        catch (IOException ex){
            System.out.println("Failed to generate file " + "'" + fileName + "'" + " !");
            ex.printStackTrace();
        }
    }

    public void generateSeq(int fileCount, String initialFolder, int boundary, int fileLength){
        File f = new File(initialFolder);
        //create folder
        f.mkdir();

        //create multiple files using our generateOne method from above
        for(int i = 0; i < fileCount; i++){
            System.out.println("Generating file " + i + ".");
            generateOne(f + "/" + i + ".txt", fileLength, boundary);
            System.out.println("Successfully generated file " + i + ".");
        }
    }

    public void generateSeqThreaded(int fileCount, String initialFolder, int boundary, int fileLength, int threadCount){
        File f = new File(initialFolder);
        //create folder
        f.mkdir();

        //create multiple files using FileGeneratorTask, using
        //threads and ExecutorService

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < fileCount; i++){
            executorService.submit(new FileGeneratorTask(initialFolder + "/" + i + ".txt", fileLength, boundary));
            System.out.println("File " + i + " generation routine was put into the executor.");
        }

        // sleep well, executorService, you served me well.
        executorService.shutdown();
    }
}
