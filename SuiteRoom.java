public class SuiteRoom extends Room {
    private double squareMetersBedroom;
    private double getSquareMetersSecondRoom;
    private double price;

    public SuiteRoom(int roomNumber, double squareMetersBedroom, double getSquareMetersSecondRoom, double price) {
        super(roomNumber);
        this.squareMetersBedroom = squareMetersBedroom;
        this.getSquareMetersSecondRoom = getSquareMetersSecondRoom;
        this.price = price;
    }

    public double getSquareMetersBedroom() {
        return squareMetersBedroom;
    }

    public void setSquareMetersBedroom(double squareMetersBedroom) {
        this.squareMetersBedroom = squareMetersBedroom;
    }

    public double getGetSquareMetersSecondRoom() {
        return getSquareMetersSecondRoom;
    }

    public void setGetSquareMetersSecondRoom(double getSquareMetersSecondRoom) {
        this.getSquareMetersSecondRoom = getSquareMetersSecondRoom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Information about the suite: room number " + getRoomNumber() + ", square meters of the bedroom: " +
                this.squareMetersBedroom + ", square meters fo the second room: " + this.getSquareMetersSecondRoom +
                ", the price is " + this.price;
    }
}
