import java.util.Locale;

public class BookingSystem {
    /**
     * It reads the input file and process them, generates logs ant writes these logs to the output file.
     *
     * @param args An array of command line arguments. Expected 2 command line arguments.
     *             args[0] = input file.
     *             args[1] = output file.
     */
    public static void main(String[] args) {
        switch (args.length) {
            case 2:
                Locale.setDefault(new Locale("en", "US"));
                // Read the input file.
                String[] inputFile = FileInput.readFile(args[0], true, true);
                InputChecker inputChecker = new InputChecker(inputFile);
                // Process input commands.
                inputChecker.processCommands();
                // Write logs to output file.
                FileOutput.writeToFile(args[1], inputChecker.getLogs(), false, false);
                break;
            default:
                System.out.println("ERROR: This program works exactly with two command line arguments, the first one is the path to the input file whereas the second one is the path to the output file. Sample usage can be as follows: \"java8 BookingSystem input.txt output.txt\". Program is going to terminate!");
        }
    }
}