public class ThreadFileReader extends Thread {
    private String filepath;
    private FileReader reader;
    private int lines;
    
    public ThreadFileReader(String filepath){
        this.filepath = filepath;
        reader = new FileReader();
        lines = FileReader.DIDNT_READ;
    }

    @Override
    public void run() {
        lines = reader.getNumberOfLines(filepath);
    }

    public int getNumberOfLines(){
        return lines;
    }

    public String getFilePath(){
        return filepath;
    }
}
