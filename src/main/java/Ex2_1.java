import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex2_1 {
    static final String txtFileDirectory = "txtfiles\\";
    static final int NUMBER_OF_THREADS = 5;

    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] paths = new String[n];
        Random rnd = new Random(seed);

        for (int i = 0; i < n; i++) {
            String filepath = txtFileDirectory + "file_" + i;
            int linecount = rnd.nextInt(bound);
            paths[i] = filepath;

            if (!createFile(filepath)) {
                System.out.println("Error creating file. Name : " + filepath);
            } else if (!writeLinesToFile(filepath, linecount)) {
                System.out.println("Error writing to file. Name : " + filepath);
            }
        }

        return paths;
    }

    private static boolean createFile(String filepath) {
        try {
            File myObj = new File(filepath);
            myObj.createNewFile();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static boolean writeLinesToFile(String filepath, int count) {
        try {
            FileWriter myWriter = new FileWriter(filepath);
            for (int i = 0; i < count; i++) {
                if (i != 0)
                    myWriter.write('\n');
                myWriter.write("Line number : " + (i + 1));
            }
            myWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static int getNumOfLines(String[] filepaths) {
        int lines = 0;
        FileReader reader = new FileReader();

        for (String filepath : filepaths) {
            int result = reader.getNumberOfLines(filepath);
            if (result == FileReader.READING_ERROR) {
                System.out.println("Error reading file. Name : " + filepath);
            } else {
                lines += result;
            }
        }

        return lines;
    }

    public int getNumOfLinesThreads(String[] filepaths) {
        int lines = 0;
        ThreadFileReader[] threads = new ThreadFileReader[NUMBER_OF_THREADS];

        for (int i = 0; i < threads.length; i++)
            threads[i] = new ThreadFileReader(txtFileDirectory);

        int currentlyreading = 0;
        boolean threadsAlive;
        do {
            threadsAlive = false;
            for (int i = 0; i < threads.length; i++) {
                if (!threads[i].isAlive()) {
                    int result = threads[i].getNumberOfLines();
                    if (result == FileReader.READING_ERROR) {
                        System.out.println("Error reading file. Name : " + threads[i].getFilePath());
                    } else if (result != FileReader.DIDNT_READ) {
                        lines += result;
                    }

                    if (currentlyreading < filepaths.length) {
                        threads[i] = new ThreadFileReader(filepaths[currentlyreading++]);
                        threads[i].run();
                        threadsAlive = true;
                    }
                } else {
                    threadsAlive = true;
                }
            }
        } while (currentlyreading < filepaths.length || threadsAlive);

        return lines;
    }

    public int getNumOfLinesThreadPool(String[] filepaths) {
        int lines = 0;
        ExecutorService pool = Executors.newFixedThreadPool(filepaths.length);
        ThreadFileReader[] threads = new ThreadFileReader[filepaths.length];
        boolean[] readdata = new boolean[threads.length];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ThreadFileReader(filepaths[i]);
            readdata[i] = false;
        }

        for (int i = 0; i < threads.length; i++) {
            pool.execute(threads[i]);
        }

        boolean threadsAlive;
        do {
            threadsAlive = false;
            for (int i = 0; i < threads.length; i++) {
                if (!threads[i].isAlive() && threads[i].getNumberOfLines() != FileReader.DIDNT_READ) {
                    if (!readdata[i]) {
                        lines += threads[i].getNumberOfLines();
                        readdata[i] = true;
                    }
                } else {
                    threadsAlive = true;
                }
            }
        } while (threadsAlive);

        pool.shutdown();
        return lines;
    }

    public static void deleteFiles(String[] filepaths) {
        for(String filepath : filepaths) {
            File file = new File(filepath);
            file.delete();
        }
    }
}
