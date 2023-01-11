import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private static String directory =  System.getProperty("user.dir") + "\\";

    public static final int READING_ERROR = -1;
    public static final int DIDNT_READ = -2;

    public FileReader(){

    }
    
    public int getNumberOfLines(String filepath){
        int lines = 0;
        try {
			Scanner scanner = new Scanner(new File(directory + filepath));

			while (scanner.hasNextLine()) {
                scanner.nextLine();
                lines++;
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			return -1;
		}
        return lines;
    }
}
