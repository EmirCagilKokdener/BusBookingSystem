import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputChecker {
    private List<String> logs;
    private String[] input;
    private ManagementSystem managementSystem;

    /**
     * Constructs a InputChecker object.
     *
     * @param input Array of input commands.
     */
    public InputChecker(String[] input) {
        this.input = input;
        this.managementSystem = new ManagementSystem();
        this.logs = new ArrayList<>();
    }

    /**
     * Processes the input commands and generates the logs based on the results.
     */
    public void processCommands() {
        if (input != null){// Checks if the input file exists or not.
            for (String line : input) {
                logs.add("COMMAND: " + line+"\n");
                String[] parts = line.split("\t");
                String command = parts[0];
                switch (command) {
                    case "INIT_VOYAGE":
                        processINIT_VOYAGE(parts);
                        break;
                    case "SELL_TICKET":
                        processSELL_TICKET(parts);
                        break;
                    case "REFUND_TICKET":
                        processREFUND_TICKET(parts);
                        break;
                    case "PRINT_VOYAGE":
                        processPRINT_VOYAGE(parts);
                        break;
                    case "CANCEL_VOYAGE":
                        processCANCEL_VOYAGE(parts);
                        break;
                    case "Z_REPORT":
                        processZ_REPORT(parts);
                        break;
                    default:
                        logs.add(String.format("ERROR: There is no command namely %s!\n", command));
                }
            }
            String line = "Z_REPORT";
            if (input.length == 0){
                managementSystem.Z_Report(logs);// If the given file is empty.
                return;
            }
            if (!line.equals(input[input.length - 1])) {// Checks if the last line is Z_REPORT or not.
                managementSystem.Z_Report(logs);
            }
        }

    }

    /**
     * Checks the data types of the parts and some conditions and calls voyages to initialize.
     *
     * @param parts The parts of the line that is separated by tabs.
     */
    private void processINIT_VOYAGE(String[] parts) {
        // Check length of the command
        if (parts.length >= 7) {
            String type = parts[1];
            String id = parts[2];
            String from = parts[3];
            String to = parts[4];
            String rows = parts[5];
            String price = parts[6];


            // Check type.
            if (!type.equals("Minibus") && !type.equals("Standard") && !type.equals("Premium")) {
                logs.add("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!\n");
                return;
            }
            if (type.equals("Minibus")) {
                if (parts.length != 7) {
                    logs.add("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!\n");
                    return;
                }
            }
            if (type.equals("Standard")) {
                if (parts.length != 8) {
                    logs.add("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!\n");
                    return;
                }
            }
            if (type.equals("Premium")) {
                if (parts.length != 9) {
                    logs.add("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!\n");
                    return;
                }
            }
            // Check id
            try {
                int parsedId = Integer.parseInt(id);
                if (parsedId < 0) {
                    logs.add(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n", id));
                    return;
                }
            } catch (NumberFormatException e) {
                logs.add(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n", id));
                return;
            }
            // Check seat rows
            try {
                int parsedRows = Integer.parseInt(rows);
                if (parsedRows < 0) {
                    logs.add(String.format("ERROR: %s is not a positive integer, number of seat rows of a voyage must be a positive integer!\n", rows));
                    return;
                }
            } catch (NumberFormatException e) {
                logs.add(String.format("ERROR: %s is not a positive integer, number of seat rows of a voyage must be a positive integer!\n", rows));
                return;
            }
            // Check price.
            try {
                float parsedPrice = Float.parseFloat(price);
                if (parsedPrice < 0) {
                    logs.add(String.format("ERROR: %s is not a positive number, price must be a positive number!\n", price));
                    return;
                }
            } catch (NumberFormatException e) {
                logs.add(String.format("ERROR: %s is not a positive number, price must be a positive number!\n", price));
                return;
            }

            if (type.equals("Standard") || type.equals("Premium")) {
                String refundCut = parts[7];
                // Check refund cut
                try {
                    int parsedRefundCut = Integer.parseInt(refundCut);
                    if (parsedRefundCut < 0 || parsedRefundCut > 100) {
                        logs.add(String.format("ERROR: %s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!\n", refundCut));
                        return;
                    }
                } catch (NumberFormatException e) {
                    logs.add(String.format("ERROR: %s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!\n", refundCut));
                    return;
                }
            }
            if (type.equals("Premium")) {
                String premiumFee = parts[8];
                // Check premium fee
                try {
                    int parsedPremiumFee = Integer.parseInt(premiumFee);
                    if (parsedPremiumFee < 0) {
                        logs.add(String.format("ERROR: %s is not a non-negative integer, premium fee must be a non-negative integer!\n", premiumFee));
                        return;
                    }
                } catch (NumberFormatException e) {
                    logs.add(String.format("ERROR: %s is not a non-negative integer, premium fee must be a non-negative integer!\n", premiumFee));
                    return;
                }

            }
            if (type.equals("Minibus")) {
                managementSystem.initializeMiniBus(id, from, to, rows, price,logs);
            } else if (type.equals("Standard")) {
                String refundCut = parts[7];
                managementSystem.initializeStandardBus(id, from, to, rows, price, refundCut,logs);
            } else if (type.equals("Premium")) {
                String refundCut = parts[7];
                String premiumFee = parts[8];
                managementSystem.initializePremiumBus(id, from, to, rows, price, refundCut, premiumFee,logs);

            }
        } else {
            logs.add("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!\n");
        }
    }

    /**
     * Checks the data types of the given parameters and controls some conditions and calls sell ticket method.
     *
     * @param parts Separated parts from the given line.
     */
    private void processSELL_TICKET(String[] parts) {
        // Check length of the command
        if (parts.length == 3) {
            String id = parts[1];
            String[] stringSeatNumberList = parts[2].split("_");
            List<Integer> integerSeatNumberList = new ArrayList<>();
            // Check id
            try {
                int parsedId = Integer.parseInt(id);
                if (parsedId < 0) {
                    logs.add(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n", id));
                    return;
                }
            } catch (NumberFormatException e) {
                logs.add(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n", id));
                return;
            }
            Set<Integer> uniqueSeatNumbers = new HashSet<>();
            for (String s : stringSeatNumberList) {
                // Check seat number
                try {
                    int seatNumber = Integer.parseInt(s);
                    if (seatNumber < 0) {
                        logs.add(String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!\n", seatNumber));
                        return;
                        // Checks if the seat numbers are unique.
                    }if (uniqueSeatNumbers.contains(seatNumber)){
                        logs.add("ERROR: Seat numbers must be unique!\n");
                        return;
                    }else {
                        uniqueSeatNumbers.add(seatNumber);
                    }

                } catch (NumberFormatException e) {
                    logs.add(String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!\n", s));
                    return;
                }
            }
            managementSystem.sellTicket(Integer.parseInt(id), convertToIntegerList(stringSeatNumberList, integerSeatNumberList), logs);
        } else {
            logs.add("ERROR: Erroneous usage of \"SELL_TICKET\" command!\n");
        }
    }

    /**
     * Checks the data types of the given parameters and controls some conditions and calls refund ticket method.
     *
     * @param parts Separated parts from the given line.
     */
    private void processREFUND_TICKET(String[] parts) {
        // Check length of the command
        if (parts.length == 3) {
            String id = parts[1];
            String[] stringSeatNumberList = parts[2].split("_");
            List<Integer> integerSeatNumberList = new ArrayList<>();
            // Check id
            try {
                int parsedId = Integer.parseInt(id);
                if (parsedId < 0) {
                    logs.add(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n", id));
                    return;
                }
            } catch (NumberFormatException e) {
                logs.add(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n", id));
                return;
            }
            Set<Integer> uniqueSeatNumbers = new HashSet<>();
            for (String s : stringSeatNumberList) {
                // Check seat number
                try {
                    int seatNumber = Integer.parseInt(s);
                    if (seatNumber < 0) {
                        logs.add(String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!\n", seatNumber));
                        return;
                        // Checks if the seat numbers are unique.
                    }if (uniqueSeatNumbers.contains(seatNumber)){
                        logs.add("ERROR: Seat numbers must be unique!\n");
                        return;
                    }else {
                        uniqueSeatNumbers.add(seatNumber);
                    }

                } catch (NumberFormatException e) {
                    logs.add(String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!\n", s));
                    return;
                }
            }
            managementSystem.refundTickets(Integer.parseInt(id), convertToIntegerList(stringSeatNumberList, integerSeatNumberList), logs);
        } else {
            logs.add("ERROR: Erroneous usage of \"REFUND_TICKET\" command!\n");
        }
    }

    /**
     * Checks the data types of the given parameters and controls some conditions and calls print voyage method.
     *
     *
     * @param parts Separated parts from the given line.
     */
    private void processPRINT_VOYAGE(String[] parts) {
        // Check length of the command
        if (parts.length == 2) {
            String id = parts[1];
            // Check id
            try {
                int parsedId = Integer.parseInt(id);
                if (parsedId < 0) {
                    logs.add(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n", id));
                    return;
                }
            } catch (NumberFormatException e) {
                logs.add(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n", id));
                return;
            }

            managementSystem.printVoyage(Integer.parseInt(id), logs);
        } else {
            logs.add("ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!\n");
        }
    }

    /**
     * Checks the data types of the given parameters and controls some conditions and calls cancel voyage method.
     *
     * @param parts Separated parts from the given line.
     */
    private void processCANCEL_VOYAGE(String[] parts) {
        // Check length of the command
        if (parts.length == 2) {
            String id = parts[1];
            // Check id
            try {
                int parsedId = Integer.parseInt(id);
                if (parsedId < 0) {
                    logs.add(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n", id));
                    return;
                }
            } catch (NumberFormatException e) {
                logs.add(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!\n", id));
                return;
            }
            managementSystem.cancelVoyage(Integer.parseInt(id), logs);
        } else {
            logs.add("ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!\n");
        }
    }

    /**
     *
     * @param parts Separated parts from the given line.
     */
    private void processZ_REPORT(String[] parts) {
        // Check length of the command
        if (parts.length == 1) {
            managementSystem.Z_Report(logs);
        } else {
            logs.add("ERROR: Erroneous usage of \"Z_REPORT\" command!\n");
        }

    }

    /**
     * Converts given string array to an integer list.
     *
     * @param stringArray The string array to be converted.
     * @param integerList The integer list that is converted from the sting array.
     * @return The integer list.
     */
    private List<Integer> convertToIntegerList(String[] stringArray, List<Integer> integerList) {
        for (String str : stringArray) {
            integerList.add(Integer.parseInt(str));
        }
        return integerList;
    }

    /**
     * Returns the list of logs.
     *
     * @return Logs.
     */
    public List<String> getLogs() {
        if (logs.size() == 0){

        }else {
            logs = logs.subList(0, logs.size() - 1);// this is for deleting the empty line at the end.
            logs.add("----------------");
        }
        return logs;
    }
}