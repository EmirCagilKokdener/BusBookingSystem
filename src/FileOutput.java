import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

public class FileOutput {
    /**
     * This method writes given content to file at given path.
     *
     * @param path    Path for the file content is going to be written.
     * @param list    List that is going to be written to file.
     * @param append  Append status, true if wanted to append to file if it exists, false if wanted to create file from zero.
     * @param newLine True if wanted to append a new line after content, false if vice versa.
     */
    public static void writeToFile(String path, List<String> list , boolean append, boolean newLine) {
        if (list.size() != 0){
            PrintStream ps = null;
            try {
                ps = new PrintStream(new FileOutputStream(path, append));
                for (String line : list){
                    ps.print(line + (newLine ? "\n" : ""));
                }
            } catch (FileNotFoundException e) {
                System.out.println("ERROR: This program cannot write to the \"<"+ path +">\", please check the permissions to write that directory. Program is going to terminate!");
            } finally {
                if (ps != null) { //Flushes all the content and closes the stream if it has been successfully created.
                    ps.flush();
                    ps.close();
                }
            }
        }
    }
}