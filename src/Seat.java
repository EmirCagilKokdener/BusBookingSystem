public class Seat {
    private int seatNumber;
    private boolean isEmpty;
    private boolean isPremium;
    public Seat(int seatNumber){
        this.seatNumber = seatNumber;
        this.isEmpty = true;
        this.isPremium = false;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }
    public void buySeat(){
        this.isEmpty = false;
    }
    public void setEmpty(){
        this.isEmpty = true;
    }
}
