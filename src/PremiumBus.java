public class PremiumBus extends Bus{
    /**
     * Constructs a premium bus object.
     *
     * @param id The id of the premium bus.
     * @param from The starting point of the premium bus route.
     * @param to The ending point of the premium bus route.
     * @param numRows The number of rows in the premium bus.
     * @param price The price of the standard ticket.
     * @param refundCut The refund cut of the ticket.
     * @param premiumFee The premium fee of the ticket.
     */
    public PremiumBus(int id, String from, String to, int numRows, float price, int refundCut, int premiumFee) {
        super(id, from, to, numRows, 3, price, refundCut, premiumFee, 0);
        updatePremiumSeats();
    }

    /**
     * Updates the premium seat of the premium bus.
     */
    private void updatePremiumSeats(){
        for (int i = 1;i<= seats.size();i+=3 ){
            seats.get(i).setPremium(true);
        }
    }

    /**
     * Type of the bus.
     *
     * @return Premium.
     */
    @Override
    public String getType() {
        return "Premium";
    }

    /**
     * Returns the string representation of the premium bus and some general  information of the premium bus.
     *
     * @return The string representation of the premium bus with some general information.
     */
    @Override
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
            if (!(seatNumber % 3 ==0)){
                sb.append(" ");
            }
            if (seatNumber % 3 == 1){
                sb.append("| ");
            }
            if (seatNumber % 3 == 0){
                sb.append("\n");
            }
        }
        sb.append("Revenue: ").append(String.format("%.2f", voyageRevenue));
        return sb.toString();
    }
}
