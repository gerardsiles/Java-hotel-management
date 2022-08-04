import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Hotel {
    private String hotelName;
    private String address;
    private String city;
    private ArrayList<Customer> customers;
    private ArrayList<Reservation> reservations;
    private ArrayList<Room> rooms;

    public Hotel(String hotelName, String address, String city) {
        this.hotelName = hotelName;
        this.address = address;
        this.city = city;
        this.customers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public void setReservations(ArrayList<Reservation> reservation) {
        this.reservations = reservation;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    // Class methods
    // Get a customer from the arraylist with ID
    public Customer getCustomer(String ID) {
        Customer customer = null;
        boolean found = false;
        Iterator<Customer> it = customers.iterator();
        while (!found && it.hasNext()){
            Customer c = it.next();
            if (c.getID().equals(ID)) {
                customer = c;
                found = true;
            }
        }
        return customer;
    }

    public Room getRoom(int number) {
        Room room = null;
        boolean found = false;
        Iterator<Room> it = rooms.iterator();
        while (!found && it.hasNext()) {
            Room r = it.next();
            if (r.getRoomNumber() == number) {
                room = r;
                found = true;
            }
        }
        return room;
    }

    // Methods to add to the ArrayList
    public void addReservation(Reservation reservation) {reservations.add(reservation);}

    public void addCustomer(Customer customer) {customers.add(customer);}

    public void addRoom(Room room) { rooms.add(room);}

    // Methods to delete from the ArrayList
    public void deleteReservation(Reservation reservation) {reservations.remove(reservation);}

    public void deleteCustomer(Customer customer) {customers.remove(customer);}

    public void deleteRoom(Room room) {rooms.remove(room);}

    // Clear information in the ArrayList
    public void deleteData() {
        reservations.clear();
        rooms.clear();
        customers.clear();
    }

    // Preload information on the application
    public static void loadData(Hotel hotel) {
        SingleRoom single;
        DoubleRoom doubleRoom;
        SuiteRoom suite;
        Room room;
        Customer customer;
        Reservation reservation;

        hotel.deleteData();

        // Rooms
        single = new SingleRoom(1, 24.5, 80.4);
        hotel.addRoom(single);
        single = new SingleRoom(4, 12.5, 78.3);
        hotel.addRoom(single);
        single = new SingleRoom(5, 20.4, 79.4);
        hotel.addRoom(single);
        doubleRoom = new DoubleRoom(2, 30.1, 100.76, "queen size");
        hotel.addRoom(doubleRoom);
        doubleRoom = new DoubleRoom(6, 30.1, 100.76, "queen size");
        hotel.addRoom(doubleRoom);
        doubleRoom = new DoubleRoom(7, 30.1, 100.76, "queen size");
        hotel.addRoom(doubleRoom);
        suite = new SuiteRoom(3, 30.8, 24.5, 145.89);
        hotel.addRoom(suite);
        suite = new SuiteRoom(8, 30.8, 24.5, 145.89);
        hotel.addRoom(suite);
        suite = new SuiteRoom(9, 30.8, 24.5, 145.89);
        hotel.addRoom(suite);

        // Customers
        customer = new Customer("Gerard", "Siles Aligue", "2345673A");
        hotel.addCustomer(customer);
        customer = new Customer("Manfredo", "Di Sicillia", "33529651R");
        hotel.addCustomer(customer);
        customer = new Customer("Farinata", "Degli Uberti", "1234567Q");
        hotel.addCustomer(customer);
        //reservas
        customer = hotel.getCustomer("2345673A");
        LocalDate entryDate = LocalDate.of(2021, 12, 22);
        LocalDate exitDate = LocalDate.of(2021, 12, 30);
        reservation = new Reservation(1, entryDate, exitDate);
        room = hotel.getRoom(1);
        reservation.addRoom(room);
        reservation.addCustomer(customer);
        hotel.addReservation(reservation);

        customer = hotel.getCustomer("2345673A");
        entryDate = LocalDate.of(2021, 12, 02);
        exitDate = LocalDate.of(2021, 12, 14);
        reservation = new Reservation(5, entryDate, exitDate);
        room = hotel.getRoom(8);
        reservation.addRoom(room);
        reservation.addCustomer(customer);
        hotel.addReservation(reservation);

        customer = hotel.getCustomer("33529651R");
        entryDate = LocalDate.of(2021, 12, 24);
        exitDate = LocalDate.of(2021, 12, 28);
        reservation = new Reservation(2, entryDate, exitDate);
        room = hotel.getRoom(4);
        reservation.addRoom(room);
        reservation.addCustomer(customer);
        hotel.addReservation(reservation);

        customer = hotel.getCustomer("1234567Q");
        entryDate = LocalDate.of(2021, 12, 17);
        exitDate = LocalDate.of(2021, 12, 22);
        reservation = new Reservation(3, entryDate, exitDate);
        room = hotel.getRoom(9);
        reservation.addRoom(room);
        reservation.addCustomer(customer);
        hotel.addReservation(reservation);

        customer = hotel.getCustomer("2345673A");
        entryDate = LocalDate.of(2021, 12, 16);
        exitDate = LocalDate.of(2021, 12, 20);
        reservation = new Reservation(4, entryDate, exitDate);
        room = hotel.getRoom(5);
        reservation.addRoom(room);
        reservation.addCustomer(customer);
        hotel.addReservation(reservation);


        System.out.println("Data loaded successfully.");
    }

    @Override
    public String toString() {
        return "Hotel information: Name " + this.hotelName + "lcoated in " + this.address + " in the city of " + this.city;
    }
}
