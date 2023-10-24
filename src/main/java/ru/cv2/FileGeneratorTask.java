package ru.cv2;

import java.util.concurrent.Callable;

public class FileGeneratorTask implements Callable<Integer> {
    private final String fileName;
    private final int length;
    private final int boundary;
    @Override
    public Integer call() throws Exception {
        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateOne(fileName, length, boundary);
        return 0;
    }

    public FileGeneratorTask(String fileName, int length, int boundary) {
        this.fileName = fileName;
        this.length = length;
        this.boundary = boundary;
    }

}
