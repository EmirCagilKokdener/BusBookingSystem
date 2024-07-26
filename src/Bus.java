import java.util.Map;
import java.util.TreeMap;

abstract class Bus {
    protected int id;
    protected String from;
    protected String to;
    protected int numRows;
    protected int setsPerRow;
    protected float price;
    protected int refundCut;
    protected int premiumFee;
    protected float voyageRevenue;
    protected Map<Integer, Seat> seats = new TreeMap<>();
    protected static Map<Integer, Bus> buses = new TreeMap<>();

    /**
     * Constructs a new bus object.
     *
     * @param id The unique id of the bus
     * @param from The starting point of the bus.
     * @param to The ending point of the bus.
     * @param numRows The number of row seats in the bus.
     * @param setsPerRow The number of seats in the row of the bus.
     * @param price The standard price of the bus ticket
     * @param refundCut The refund cut of the tickets of the bus.
     * @param premiumFee The premium fee of the tickets of the bus.
     * @param voyageRevenue The total money that bus made from selling and refunding tickets.
     */

    public Bus(int id, String from, String to, int numRows, int setsPerRow, float price, int refundCut, int premiumFee, float voyageRevenue) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.numRows = numRows;
        this.setsPerRow = setsPerRow;
        this.price = price;
        this.refundCut = refundCut;
        this.premiumFee = premiumFee;
        this.voyageRevenue = 0;
        makeSeats();
        buses.put(id, this);
    }

    /**
     * Return bus by its id.
     *
     * @param id The unique id of the bus.
     * @return The bus.
     */
    public static Bus getBus(int id) {
        return buses.get(id);
    }

    /**
     * Create seats with the number of rows in the bus and seats per row in the bus.
     */
    private void makeSeats() {
        int seatNumber = 1;
        for (int i = 1; i <= numRows; i++) {
            for (int j = 1; j <= setsPerRow; j++) {
                seats.put(seatNumber, new Seat(seatNumber));
                seatNumber++;
            }
        }
    }

    /**
     * Returns The standard price of a ticket.
     *
     * @return The standard price of a ticket
     */
    public float getTicketPrice() {
        return price;
    }

    /**
     * Returns the premium fee of the ticket.
     *
     * @return The premium fee of the ticket.
     */
    public int getPremiumFee() {
        return premiumFee;
    }

    /**
     * Reurns the starting point of the bus.
     *
     * @return The staring point of the bus.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the ending point of the bus route.
     *
     * @return Ending point of the bus route.
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the refund cut of the ticket.
     *
     * @return Returns the refund cut of the ticket.
     */
    public int getRefundCut() {
        return refundCut;
    }

    /**
     * Add the ticket price to the voyage revenue.
     *
     * @param ticketPrice The ticket price.
     * @return Updated voyage revenue.
     */
    public float addVoyageRevenue(float ticketPrice) {
        voyageRevenue += ticketPrice;
        return voyageRevenue;
    }

    /**
     * Substracts the ticket price form the total revenue.
     *
     * @param ticketPrice The ticket price.
     * @return Updated voyage revenue
     */
    public float subtractVoyageRevenue(float ticketPrice) {
        voyageRevenue -= ticketPrice;
        return voyageRevenue;
    }

    /**
     * Returns the type of the bus.
     *
     * @return The type of the bus.
     */
    public abstract String getType();

    /**
     * Returns the string representation of the bus and some general  information of the bus
     *
     * @return The string representation of the bus and some general  information of the bus
     */
    public abstract String toString();
}