package ru.cv2;

import java.util.concurrent.Callable;

public class FileReadTask implements Callable<Long> {
    private String fileName;

    @Override
    public Long call() throws Exception {
        FileReader reader = new FileReader();
        return reader.read(fileName);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileReadTask(String fileName) {
        this.fileName = fileName;
    }
}
