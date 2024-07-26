import java.util.List;
import java.util.Map;
import java.util.Set;

public class ManagementSystem {
    /**
     * Creates a Minibus object with given parameters and writes to the logs.
     *
     * @param id The id of the minibus.
     * @param from The starting point of the mini bus route.
     * @param to The ending point of the minibus route.
     * @param rows The number of rows in the minibus.
     * @param price The standard price of the ticket.
     * @param logs The generated logs from the method.
     * @return The minibus object.
     */
    public Bus initializeMiniBus(String id, String from, String to, String rows, String price, List<String> logs) {
        int parsedId = Integer.parseInt(id);
        // Checks if bus already exists.
        if (isBusExists(parsedId)) {
            logs.add(String.format("ERROR: There is already a voyage with ID of %s!\n", id));
            return null;
        } else {
            float parsedPrice = Float.parseFloat(price);
            int parsedRows = Integer.parseInt(rows);
            logs.add(String.format("Voyage %s was initialized as a minibus (2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that minibus tickets are not refundable.\n", id, from, to, ((float) Math.round(parsedPrice * 100) / 100), parsedRows * 2));
            return new MiniBus(parsedId, from, to, parsedRows, parsedPrice, 0, 0);
        }
    }

    /**
     * Creates a Standard bus object with given parameters and writes to the logs.
     *
     * @param id The id of the minibus.
     * @param from The starting point of the mini bus route.
     * @param to The ending point of the minibus route.
     * @param rows The number of rows in the minibus.
     * @param price The standard price of the ticket.
     * @param refundCut The refund cut of the ticket.
     * @param logs The generated logs from the method.
     * @return The Standard bus object.
     */
    public Bus initializeStandardBus(String id, String from, String to, String rows, String price, String refundCut, List<String> logs) {
        int parsedId = Integer.parseInt(id);
        // Checks if the bus already exists
        if (isBusExists(parsedId)) {
            logs.add(String.format("ERROR: There is already a voyage with ID of %s!\n", id));
            return null;
        } else {
            float parsedPrice = Float.parseFloat(price);
            int parsedRows = Integer.parseInt(rows);
            int parsedRefundCut = Integer.parseInt(refundCut);
            logs.add(String.format("Voyage %s was initialized as a standard (2+2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that refunds will be %d%% less than the paid amount.\n", id, from, to, ((float) Math.round(parsedPrice * 100) / 100), parsedRows * 4, parsedRefundCut));
            return new StandardBus(parsedId, from, to, parsedRows, parsedPrice, parsedRefundCut, 0);
        }
    }
    /**
     * Creates a Premium bus object with given parameters and writes to the logs.
     *
     * @param id The id of the minibus.
     * @param from The starting point of the mini bus route.
     * @param to The ending point of the minibus route.
     * @param rows The number of rows in the minibus.
     * @param price The standard price of the ticket.
     * @param refundCut The refund cut of the ticket.
     * @param premiumFee The premium fee of the ticket.
     * @param logs The generated logs from the method.
     * @return The Premium bus object.
     */
    public Bus initializePremiumBus(String id, String from, String to, String rows, String price, String refundCut, String premiumFee, List<String> logs) {
        int parsedId = Integer.parseInt(id);
        // Checks if the bus already exists.
        if (isBusExists(parsedId)) {
            logs.add(String.format("ERROR: There is already a voyage with ID of %s!\n", id));
            return null;
        } else {
            float parsedPrice = Float.parseFloat(price);
            int parsedRows = Integer.parseInt(rows);
            int parsedRefundCut = Integer.parseInt(refundCut);
            int parsedPremiumFee = Integer.parseInt(premiumFee);
            float premiumTicket = (float) (parsedPrice + (parsedPrice * parsedPremiumFee / 100.0));
            logs.add(String.format("Voyage %s was initialized as a premium (1+2) voyage from %s to %s with %.2f TL priced %d regular seats and %.2f TL priced %d premium seats. Note that refunds will be %d%% less than the paid amount.\n", id, from, to, ((float) Math.round(parsedPrice * 100) / 100), parsedRows * 2, ((float) Math.round(premiumTicket * 100) / 100), parsedRows * 1, parsedRefundCut));
            return new PremiumBus(parsedId, from, to, parsedRows, parsedPrice, parsedRefundCut, parsedPremiumFee);
        }
    }

    /**
     * Checks bus with the given id exists or not.
     *
     * @param busId The id of the bus
     * @return True if the bus exists.
     */
    private boolean isBusExists(int busId) {
        return Bus.getBus(busId) != null;
    }

    /**
     * Checks if the seat exists in the bus
     *
     * @param bus The bus object.
     * @param seatNumber The seat number of the seat.
     * @return True if seat exists.
     */
    private boolean isSeatExists(Bus bus, int seatNumber) {
        return bus.seats.get(seatNumber) != null;
    }

    /**
     * Checks if the seat is empty or not.
     *
     * @param seat The seat to check.
     * @return True if seat is empty.
     */
    private boolean isSeatEmpty(Seat seat) {
        return seat.isEmpty();
    }

    /**
     * Adds (-) between integers in the  integer list.
     *
     * @param seatNumbers List of seat numbers in the integer format.
     * @return Dashed integer list in the string format.
     */
    private String addDash(List<Integer> seatNumbers) {
        StringBuilder dashedSeats = new StringBuilder();
        for (int i = 0; i < seatNumbers.size(); i++) {
            dashedSeats.append(seatNumbers.get(i));
            if (i < (seatNumbers.size() - 1)) {
                dashedSeats.append("-");
            }
        }
        return dashedSeats.toString();
    }

    /**
     * First checks if bus exists then checks if the seat exists then checks if it is empty or not if all seats are available sells the ticket and writes the logs.
     *
     * @param busId The id of the bus.
     * @param seatNumbers The seat number of the seat.
     * @param logs The generated logs.
     */
    public void sellTicket(int busId, List<Integer> seatNumbers, List<String> logs) {
        // Checks if the bus exists
        if (!isBusExists(busId)) {
            logs.add(String.format("ERROR: There is no voyage with ID of %s!\n", busId));
            return;
        }
        Bus bus = Bus.getBus(busId);
        boolean isAllSeatsAvailable = true;
        for (int seatNumber : seatNumbers) {
            // Checks if the seat exists.
            if (!isSeatExists(bus, seatNumber)) {
                if (seatNumbers.size() == 1) {
                    logs.add("ERROR: There is no such a seat!\n");
                } else {
                    logs.add("ERROR: One or more seats doesn't exists!\n");// if there is more than one seat number.
                }
                isAllSeatsAvailable = false;
                break;
            }
            Seat seat = bus.seats.get(seatNumber);
            // Checks if the seat is empty.
            if (!isSeatEmpty(seat)) {
                if (seatNumbers.size() == 1) {
                    logs.add("ERROR: The seat is already sold!\n");
                } else {
                    logs.add("ERROR: One or more seats already sold!\n");
                }
                isAllSeatsAvailable = false;
                break;
            }
        }
        // Sells when all seats are available
        if (isAllSeatsAvailable) {
            float totalMoneyFromSelling = 0;
            for (int seatNumber : seatNumbers) {
                bus.seats.get(seatNumber).buySeat();
                float ticketPrice = bus.getTicketPrice();
                if (bus.seats.get(seatNumber).isPremium()) {// Checks the seat if it is premium.
                    float premiumTicketPrice = (ticketPrice + (ticketPrice * bus.getPremiumFee() / 100));
                    bus.addVoyageRevenue(premiumTicketPrice);
                    totalMoneyFromSelling += premiumTicketPrice;
                } else {
                    bus.addVoyageRevenue(ticketPrice);
                    totalMoneyFromSelling += ticketPrice;
                }
            }
            logs.add(String.format("Seat %s of the Voyage %s from %s to %s was successfully sold for %.2f TL.\n", addDash(seatNumbers), busId, bus.getFrom(), bus.getTo(), ((float) Math.round(totalMoneyFromSelling * 100) / 100)));
        }
    }

    /**
     * First checks if bus exists then checks if the seat exists then checks if it is empty or not if all seats are available refunds the tickets and writes the logs.
     *
     * @param busId The id of the bus.
     * @param seatNumbers The seat numbers of the seat.
     * @param logs The generated logs.
     */
    public void refundTickets(int busId, List<Integer> seatNumbers, List<String> logs) {
        // Checks if the bus exists.
        if (!isBusExists(busId)) {
            logs.add(String.format("ERROR: There is no voyage with ID of %s!\n", busId));
            return;
        }
        Bus bus = Bus.getBus(busId);
        if (bus.getType().equals("Minibus")) {
            logs.add("ERROR: Minibus tickets are not refundable!\n");
            return;
        }
        boolean isAllSeatsAvailable = true;
        for (int seatNumber : seatNumbers) {
            // Checks if the seat exists
            if (!isSeatExists(bus, seatNumber)) {
                if (seatNumbers.size() == 1) {
                    logs.add("ERROR: There is no such a seat!\n");
                } else {
                    logs.add("ERROR: One or more seats doesn't exists!\n");// if there is more than one seat number.
                }
                isAllSeatsAvailable = false;
                break;
            }
            Seat seat = bus.seats.get(seatNumber);
            // Checks if the seat is empty.
            if (isSeatEmpty(seat)) {
                if (seatNumbers.size() == 1) {
                    logs.add("ERROR: The seat is already empty!\n");
                } else {
                    logs.add("ERROR: One or more seats are already empty!\n");
                }
                isAllSeatsAvailable = false;
                break;
            }
        }
        // Refunds if all seats are refundable
        if (isAllSeatsAvailable) {
            float totalRefunds = 0;
            for (int seatNumber : seatNumbers) {
                bus.seats.get(seatNumber).setEmpty();
                float ticketPrice = bus.getTicketPrice();
                int refundCut = bus.getRefundCut();
                if (bus.seats.get(seatNumber).isPremium()) {
                    float premiumTicketPrice = (ticketPrice + (ticketPrice * bus.getPremiumFee() / 100));
                    float premiumRefunds = premiumTicketPrice - ((refundCut * premiumTicketPrice) / 100);
                    bus.subtractVoyageRevenue(premiumRefunds);
                    totalRefunds += premiumRefunds;

                } else {
                    float refunds = ticketPrice - ((ticketPrice * refundCut) / 100);
                    bus.subtractVoyageRevenue(refunds);
                    totalRefunds += refunds;
                }
            }
            logs.add(String.format("Seat %s of the Voyage %s from %s to %s was successfully refunded for %.2f TL.\n", addDash(seatNumbers), busId, bus.getFrom(), bus.getTo(), (((float) Math.round(totalRefunds * 100)) / 100)));
        }
    }

    /**
     * First it checks if the voyage exists if it exists prints the voyage
     *
     * @param busId The id of the bus.
     * @param logs Generated logs.
     */
    public void printVoyage(int busId, List<String> logs) {
        // Cehcks if the bus exists.
        if (!isBusExists(busId)) {
            logs.add(String.format("ERROR: There is no voyage with ID of %s!\n", busId));
            return;
        }
        Bus bus = Bus.getBus(busId);
        logs.add(bus.toString() + "\n");
    }

    /**
     * First checks if the voyage exists if it exists then cancels the voyage.
     *
     * @param busId The id of the bus.
     * @param logs The generated logs.
     */
    public void cancelVoyage(int busId, List<String> logs) {
        // Checks if the bus exists.
        if (!isBusExists(busId)) {
            logs.add(String.format("ERROR: There is no voyage with ID of %s!\n", busId));
            return;
        }
        Bus bus = Bus.getBus(busId);
        float ticketPrice = bus.getTicketPrice();
        int premiumFee = bus.getPremiumFee();
        for (int seatNumber = 1; seatNumber <= bus.seats.size(); seatNumber++) {
            if (!(bus.seats.get(seatNumber).isEmpty())) {
                if (bus.seats.get(seatNumber).isPremium()) {
                    float premiumTicketPrice = (ticketPrice + (ticketPrice * premiumFee / 100));
                    bus.subtractVoyageRevenue(premiumTicketPrice);
                } else {
                    bus.subtractVoyageRevenue(ticketPrice);
                }
            }
        }
        logs.add(String.format("Voyage %s was successfully cancelled!\n", busId));
        logs.add("Voyage details can be found below:\n");
        logs.add(bus.toString() + "\n");
        Bus.buses.remove(busId);
    }

    /**
     * Prints Z_report.
     *
     * @param logs The generated logs.
     */
    public void Z_Report(List<String> logs) {
        logs.add("Z Report:\n");
        logs.add("----------------\n");
        Set<Map.Entry<Integer, Bus>> entrySet = Bus.buses.entrySet();

        // Check if there are no buses
        if (entrySet.isEmpty()) {
            logs.add("No Voyages Available!\n");
            logs.add("----------------\n");
            return; //
        }

        for (Map.Entry<Integer, Bus> entry : entrySet) {
            int busId = entry.getKey(); // Get the bus ID from the entry
            Bus bus = entry.getValue();// Get the bus object from the entry
            logs.add(bus.toString() + "\n");
            logs.add("----------------\n");

        }
    }
}