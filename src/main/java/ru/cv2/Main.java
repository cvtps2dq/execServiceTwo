package ru.cv2;

public class Main {
    //constants
    private final static int fileCount = 13;
    private final static int threadCount = 6;
    private final static int fileLength = 10_000_000;
    private final static int fileBoundary = 1_000_000;
    private final static String folder = "files";

    public static void main(String[] args) {

        System.out.println("Generating files.");
        //load our fileGenerator and generate files
        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.generateSeq(fileCount, folder, fileBoundary, fileLength);
        System.out.println("Generated files.");

        //non-thread read
        NonThreadRead ntr = new NonThreadRead();
        ntr.execute(folder, fileCount);

        //threaded read
        ThreadRead tr = new ThreadRead();
        tr.execute(folder, fileCount, threadCount);
        }
    }
