package ru.cv2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
}
