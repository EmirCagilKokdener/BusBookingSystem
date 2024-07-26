public class MiniBus extends Bus{
    /**
     * Constructs a minibus object.
     *
     * @param id The id of the minibus.
     * @param from The starting point of the minibus route.
     * @param to The ending point of the minibus route.
     * @param numRows The number of rows in the minibus.
     * @param price The price of the standard ticket.
     * @param refundCut The refund cut of the ticket.
     * @param premiumFee The premium fee of the ticket.
     */
    public MiniBus(int id, String from, String to, int numRows, float price, int refundCut,int premiumFee) {
        super(id, from, to, numRows, 2, price, 0, 0, 0);
    }

    /**
     * Returns the type of the bus.
     *
     * @return Minibus.
     */
    @Override
    public String getType() {
        return "Minibus";
    }

    /**
     * Returns the string representation of the minibus and some general  information of the minibus.
     *
     * @return The string representation of the minibus with some general information.
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
            if (seatNumber % 2 ==1){
                sb.append(" ");
            }
            if (seatNumber % 2 == 0){
                sb.append("\n");
            }
        }
        sb.append("Revenue: ").append(String.format("%.2f", voyageRevenue));
        return sb.toString();
    }
}
