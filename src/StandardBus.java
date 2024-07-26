public class StandardBus extends Bus {
    /**
     * Constructs a standard bus object.
     *
     * @param id The id of the standard bus.
     * @param from The starting point of the standard bus route.
     * @param to The ending point of the standard bus route.
     * @param numRows The number of rows in the standard bus.
     * @param price The price of the standard ticket.
     * @param refundCut The refund cut of the ticket.
     * @param premiumFee The premium fee of the ticket.
     */
    public StandardBus(int id, String from, String to, int numRows, float price, int refundCut, int premiumFee) {
        super(id, from, to, numRows, 4, price, refundCut, 0, 0);
    }

    /**
     * Returns the type.
     *
     * @return Standard.
     */
    @Override
    public String getType() {
        return "Standard";
    }
    /**
     * Returns the string representation of the standard bus and some general  information of the pstandard bus.
     *
     * @return The string representation of the standard bus with some general information.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Voyage "+id+"\n");
        sb.append(from+"-"+to+"\n");
        for (int seatNumber = 1; seatNumber <= seats.size();seatNumber++){
            if (seats.get(seatNumber).isEmpty()){
                sb.append("*");
            }else {
                sb.append("X");
            }
            if (!(seatNumber % 4 == 0)){
                sb.append(" ");
            }
            if (seatNumber % 4 == 2){
                sb.append("| ");
            }
            if (seatNumber % 4 == 0){
                sb.append("\n");
            }
        }
        sb.append("Revenue: ").append(String.format("%.2f", voyageRevenue));
        return sb.toString();
    }
}
