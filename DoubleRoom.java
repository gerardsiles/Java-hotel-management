public class DoubleRoom extends Room{
    private double squareMeters;
    private double price;
    private String bedStyle;

    public DoubleRoom(int roomNumber, double squareMeters, double price, String bedStyle) {
        super(roomNumber);
        this.squareMeters = squareMeters;
        this.price = price;
        this.bedStyle = bedStyle;
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

    public String getBedStyle() {
        return bedStyle;
    }

    public void setBedStyle(String bedStyle) {
        this.bedStyle = bedStyle;
    }

    @Override
    public String toString() {
        return "Information about the double room: room number " + getRoomNumber() + ", the size is " +
                " square meters, the bed is " + this.bedStyle + ", and the price for the room is " + this.price;
    }
}
