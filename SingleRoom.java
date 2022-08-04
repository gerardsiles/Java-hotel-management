public class SingleRoom extends Room{
    private double squareMeters;
    private double price;

    public SingleRoom(int roomNumber, double squareMeters, double price) {
        super(roomNumber);
        this.squareMeters = squareMeters;
        this.price = price;
    }

    public double getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(double squareMeters) {
        this.squareMeters = squareMeters;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Information about the single room: " + getRoomNumber() + ", size of " + this.squareMeters +
                " square meters, price: " + this.price;
    }
}
