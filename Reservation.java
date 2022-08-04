import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reservation {
    private int reservationNumber;
    private LocalDate entryDate;
    private LocalDate exitDate;
    private ArrayList<Customer> customer;
    private ArrayList<Room> room;

    public Reservation(int reservationNumber, LocalDate entryDate, LocalDate exitDate) {
        this.reservationNumber = reservationNumber;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.customer = new ArrayList<>();
        this.room = new ArrayList<>();
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public ArrayList<Customer> getCustomer() {
        return customer;
    }

    public ArrayList<Room> getRoom() {
        return room;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public void setCustomer(ArrayList<Customer> customer) {
        this.customer = customer;
    }

    public void setRoom(ArrayList<Room> room) {
        this.room = room;
    }

    public void addCustomer(Customer c) {
        customer.add(c);
    }

    public void addRoom(Room r) {
        room.add(r);
    }

    // Get the last reservation
    public static int lastReservation(Hotel hotel) {
        int lastReservationNumber = 0;
        ArrayList<Reservation> reservation = hotel.getReservations();
        // go to the last position, get last number and update to new number
        lastReservationNumber = (reservation.get(reservation.size() - 1).getReservationNumber()) + 1;
        return lastReservationNumber;
    }
    // Parse LocalDate
    public static LocalDate parseDate(String string) {
        LocalDate dateToParse = null;
        do try {
            dateToParse = LocalDate.parse(string);
        } catch (DateTimeParseException e) {
            System.out.println("Enter a right date value (YYYY-mm-dd)" + e);
        }   while (dateToParse == null);

        return dateToParse;
    }

    // Check if a room is available for a reservation
    public static boolean roomAvailable (LocalDate entryNewReservation, LocalDate exitExistingReservation) {
        return entryNewReservation.isAfter(exitExistingReservation) &&
                entryNewReservation.isEqual(exitExistingReservation);
    }

    // Check if a date is in a period of time
    public static boolean dateInPeriod(LocalDate startPeriod, LocalDate endPeriod, LocalDate entryDate,
                                       LocalDate exitDate) {
        return exitDate.isAfter(startPeriod) && entryDate.isBefore(endPeriod);
    }

    // Check rooms available in a period of time, filtered by type of room
    public static void roomsPeriod(Hotel hotel, LocalDate startPeriod, LocalDate endPeriod) {
        for (Room room : hotel.getRooms()) {
            if (room instanceof SingleRoom) {
                roomsPeriodCheck(hotel, startPeriod, endPeriod, room);
            }
        }

        for (Room room : hotel.getRooms()) {
            if (room instanceof DoubleRoom) {
                roomsPeriodCheck(hotel, startPeriod, endPeriod, room);
            }
        }

        for (Room room : hotel.getRooms()) {
            roomsPeriodCheck(hotel, startPeriod, endPeriod, room);
        }
    }

    public static void roomsPeriodCheck(Hotel hotel, LocalDate startPeriod, LocalDate endPeriod, Room room) {
        boolean occupied = false;
        for (Reservation reservation : hotel.getReservations()) {
            LocalDate entry = reservation.getEntryDate();

            LocalDate exit = reservation.getExitDate();

            // If the reservation is in the same period of time and is the same room
            if ((dateInPeriod(startPeriod, endPeriod, entry, exit)) && (reservation.getRoom().contains(room))) {
                occupied = true;
                break;
            }
        }
        if (!occupied) {
            System.out.println(room);
        }
    }

    public static void deleteReservation(Hotel hotel, int reservationNumber) {
        for (Reservation reservation : hotel.getReservations()) {
            if (reservation.getReservationNumber() == reservationNumber) {
                hotel.deleteReservation(reservation);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Number of the reservation: " + this.reservationNumber + ", the customer is " + this.customer +
                " in the room " + this.room + ", entry date: " + this.entryDate + ", exit date: " + this.exitDate;
    }
}
