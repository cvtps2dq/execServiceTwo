package ru.cv2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
}
