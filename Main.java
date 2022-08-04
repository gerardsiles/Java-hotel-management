import java.time.LocalDate;
import java.util.Scanner;


public class Main {
    // Create hotel object, reference to Dante's inferno
    private static final Hotel ho = new Hotel ("Dante resort", "Street of florentino Dante Alighieri, number 3", "Limbo");
    private static boolean exit;

    public static void main (String[] args) throws Exception {
        // preload data
        Hotel.loadData(ho);
        // menu
        runMenu();
    }

    // run the menu
    public static void runMenu() throws Exception{
        while(!exit){
            printMenu();
            int choice = getInput();
            performAction(choice);
        }
    }

    // Print the menu
    private static void printMenu() {
        System.out.println("\nAvailable options: ");
        System.out.println("-------------------");
        System.out.println("1. Manage reservations");
        System.out.println("2. Manage rooms");
        System.out.println("3. Manage customers");
        System.out.println("4. Available rooms in a period of time.");
        System.out.println("5. Reservations in a period of time.");
        System.out.println("6. Reservations by customers in a period.");
        System.out.println("To exit press 0");
    }

    // Read for input
    private static int getInput() throws NumberFormatException{
        Scanner kb = new Scanner(System.in);
        int choice = -1;
        while(choice < 0 || choice > 6){
            try {
                System.out.print("\nChoose the option: \n");
                choice = Integer.parseInt(kb.nextLine());
            }
            catch(NumberFormatException e){
                System.out.println("Enter a valid option\n");
            }
        }
        return choice;
    }

    // Switch statement for the options
    private static void performAction(int choice) throws Exception {
        switch (choice) {
            case 0 -> exit = true;
            case 1 -> manageReservations(ho);
            case 2 -> manageRooms(ho);
            case 3 -> manageCustomers(ho);
            case 4 -> availableRoomsPeriod(ho);
            case 5 -> reservationsPeriod(ho);
            case 6 -> reservationsCustomerPeriod(ho);
            default -> System.out.println("Unknown error");
        }
    }

    // Submenu for reservations
    private static void manageReservations(Hotel hotel) throws Exception {
        while(!exit){
            menuReservations();
            int choice = getInput();
            actionReservations(hotel, choice);
        }
    }

    // print submenu
    private static void menuReservations() {
        System.out.println("\nChoose the option: ");
        System.out.println("---------------------");
        System.out.println("0. Back to the main menu");
        System.out.println("1. Create a new reservation");
        System.out.println("2. Delete a reservation");
        System.out.println("3. List reservations");
    }

    // submenu action
    private static void actionReservations(Hotel hotel, int choice) throws Exception {
        switch(choice){
            case 0 -> runMenu();
            case 1 -> createReservation(hotel);
            case 2 -> deleteReservation(hotel);
            case 3 -> listReservations(hotel);
            default -> System.out.println("Unknown error");
        }
    }

    // Create a reservation
    private static void createReservation(Hotel hotel) {
        Reservation reservation;
        Customer customer = null;
        Room room;
        int roomNumber = 0;
        boolean reservationFinished = false;

        Scanner sc = new Scanner(System.in);

        // Get user input
        System.out.print("Input the customer's ID: ");
        String c = sc.nextLine();
        // check if the customer already exists
        try {
            if (Customer.customerExists(hotel, c)) {
                customer = hotel.getCustomer(c);
                System.out.println(customer);
            } else {
                // Inform that the customer doesn't exist
                System.out.println("This customer is not in the database");
                runMenu();
            }
        } catch (Exception e) {
            System.err.println("Enter a valid ID" + e);
        }

        // Get reservation number
        int reservationNumber = Reservation.lastReservation(ho);
        System.out.println("Number of the reservation: " + reservationNumber);

        // get start date
        LocalDate startDate;
        do {
            System.out.print("Enter the starting date (format yyyy-mm-dd): ");
            startDate = Reservation.parseDate(sc.nextLine());
            //check that the date is valid
        } while ((startDate == null) || (startDate.isBefore(LocalDate.now())));

        LocalDate endDate;
        do {
            System.out.print("Enter the exit date (format yyyy-mm-dd): ");
            endDate = Reservation.parseDate(sc.nextLine());
            // check that the end date is valid
        } while (endDate == null || endDate.isBefore(startDate));

        // Show the rooms that are available
        System.out.println("The available rooms for these period are: ");
        System.out.println("--------------------------------------------");
        // TODO
        // load available rooms into an array, and if array is empty, run menu again, else, continue
        Reservation.roomsPeriod(hotel, startDate, endDate);

        // Get the room
        do {
            try {
                System.out.print("Choose the room: ");
                roomNumber = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.err.println("Enter a valid room" + e);
            }

            room = hotel.getRoom(roomNumber);

            //Check if the room is in other reservations
            for (Reservation res : hotel.getReservations()) {
                if (roomNumber == room.getRoomNumber()) {
                    // date to be checked
                    LocalDate exitDateCheck = res.getExitDate();
                    // Check if is available
                    if (Reservation.roomAvailable(startDate, exitDateCheck)) {
                        // if is available, add it to the reservation
                        System.out.println("Added to the reservation the room number " + roomNumber);
                        // Create reservation
                        reservation = new Reservation(reservationNumber, startDate, endDate);
                        reservation.addCustomer(customer);
                        reservation.addRoom(room);
                        System.out.println(room);
                        hotel.addReservation(reservation);
                        reservationFinished = true;
                    } else {
                        System.out.println("That room is not available for these dates");
                    }
                }
            }
        } while (!reservationFinished);
    }

    // Delete a reservation
    private static void deleteReservation(Hotel hotel) {
        boolean reservationDeleted = false;
        int reservationNumber = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------------");
        System.out.println("    Delete reservation    ");
        System.out.println("--------------------------");

        do try {
            System.out.print("Enter the reservation number to be deleted: ");
            reservationNumber = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Enter a valid number");
        } while (reservationNumber == 0);

        for (Reservation res : hotel.getReservations()) {
            if (res.getReservationNumber() == reservationNumber) {
                Reservation.deleteReservation(hotel, reservationNumber);
                System.out.print("The reservation has been deleted");
                reservationDeleted = true;
                break;
            }
        }
        if (!reservationDeleted) {
            System.out.println("The reservation was not found");
        }

    }

    // list reservations
    private static void listReservations(Hotel hotel) {

        System.out.println("--------------------------");
        System.out.println("   List of reservations   ");
        System.out.println("--------------------------");

        for (Reservation reservation : hotel.getReservations()) {
            System.out.println(reservation);
        }
    }

    // Submenu
    private static void manageRooms(Hotel hotel) throws Exception {
        while(!exit){
            menuRooms();
            int choice = getInput();
            actionRooms(hotel, choice);
        }
    }

    private static void menuRooms() {
        System.out.println("\nChoose the option: ");
        System.out.println("-------------------");
        System.out.println("0. Back to the main menu");
        System.out.println("1. Create a new room");
        System.out.println("2. Delete a room");
        System.out.println("3. List rooms");
    }

    private static void actionRooms (Hotel hotel, int choice) throws Exception {
        switch(choice){
            case 0 -> runMenu();
            case 1 -> createRoom(hotel);
            case 2 -> deleteRoom(hotel);
            case 3 -> listRooms(hotel);
            default -> System.out.println("Unknown error");
        }
    }
    // End submenu



    // Create room
    private static void createRoom(Hotel hotel) throws Exception {
        Scanner sc = new Scanner(System.in);
        int roomNumber = 0;

        try {
            System.out.print("Enter the room number: ");
            roomNumber = Integer.parseInt(sc.nextLine());
        } catch (Exception e){
            System.err.println("Enter a valid number\n" + e);
        }

        // Check if the room exists
        if (Room.roomExists(roomNumber, hotel)) {
            System.out.println("This room already exists.");
            menuRooms();
        } else {

            System.out.println("Enter the type of the room");
            System.out.println("--------------------------");
            System.out.println("0. Back to main menu");
            System.out.println("1. Create simple room");
            System.out.println("2. Create double room");
            System.out.println("3. Create suite");

            int choice = -1;
            while (choice < 0 || choice > 3) {
                try {
                    System.out.print("\nChoose the option: \n");
                    choice = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("Enter a valid number\n");
                }
                switch (choice) {
                    case 0 -> runMenu();
                    case 1 -> createSingle(hotel, roomNumber);
                    case 2 -> createDouble(hotel, roomNumber);
                    case 3 -> createSuite(hotel, roomNumber);
                    default -> System.out.println("Unknown error");
                }
            }
        }
    }

    // Create single room
    private static void createSingle(Hotel hotel, int number) {
        SingleRoom single;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the square meters: ");
        double meters = sc.nextDouble();
        System.out.print("Enter the price: ");
        double price = sc.nextDouble();
        single = new SingleRoom(number, meters, price);
        hotel.addRoom(single);
        System.out.println("Room created: " + single);
    }

    // Create double room
    private static void createDouble(Hotel hotel, int number) {
        DoubleRoom doubleRoom;
        Scanner sc = new Scanner(System.in);
        double meters = 0, price = 0;


        do try {
            System.out.print("Enter the square meters: ");
            meters = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            System.err.println("Enter a valid number.\n" + e);
        }  while (meters == 0);
        do try {
            System.out.print("Enter the price: ");
            price = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            System.err.println("Enter a valid number.\n" + e);
        } while (price == 0);
        System.out.print("IEnter the type of the bed: ");
        String bedType = sc.nextLine();
        doubleRoom = new DoubleRoom(number, meters, price, bedType);
        hotel.addRoom(doubleRoom);
        System.out.println("Room created: " + doubleRoom);
    }

    // Create suite room
    private static void createSuite(Hotel hotel, int number) {
        SuiteRoom suite;
        Scanner sc = new Scanner(System.in);
        double bedroomMeters = 0.0, roomMeters = 0.0, price = 0.0;

        do try {
            System.out.print("Enter the square meters of the bedroom: ");
            bedroomMeters = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            System.err.println("Enter a valid number.\n" + e);
        } while (bedroomMeters == 0);
        do try {
            System.out.print("Enter the square meters of the additional room: ");
            roomMeters = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            System.err.println("Enter a valid number.\n" + e);
        } while (roomMeters == 0);
        do try {
            System.out.print("Enter the price: ");
            price = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            System.err.println("Enter a valid number.\n" + e);
        } while (price == 0);
        suite = new SuiteRoom(number, bedroomMeters, roomMeters, price);
        hotel.addRoom(suite);
        System.out.println("Room created: " + suite);
    }

    // Delete room
    private static void deleteRoom(Hotel hotel) {
        boolean roomDeleted = false;
        int roomNumberDelete = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------------------");
        System.out.println("      Delete room      ");
        System.out.println("-----------------------");
        System.out.print("Enter the room number to be deleted: ");
        do try {
            roomNumberDelete = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Enter a valid number");
        } while (roomNumberDelete == 0);

        for (Room room : hotel.getRooms()) {
            if (roomNumberDelete == room.getRoomNumber()) {
                Room.deleteRoom(hotel, roomNumberDelete);
                System.out.print("Room was deleted.");
                roomDeleted = true;
                break;
            }
        }
        if (!roomDeleted) {
            System.out.println("The room was not found");
        }

    }

    // List rooms filtered by type
    private static void listRooms(Hotel hotel) {
        SingleRoom single;
        DoubleRoom doubleRoom;
        SuiteRoom suite;
        System.out.println("--------------------------");
        System.out.println("       List of rooms       ");
        System.out.println("--------------------------");

        for (Room room : hotel.getRooms()) {
            if (room instanceof SingleRoom) {
                single = (SingleRoom) room;
                System.out.println(single);
            } else if (room instanceof DoubleRoom) {
                doubleRoom = (DoubleRoom) room;
                System.out.println(doubleRoom);
            } else if (room instanceof SuiteRoom) {
                suite = (SuiteRoom) room;
                System.out.println(suite);
            }
        }
    }

    // Submenu for customer
    private static void manageCustomers(Hotel hotel) throws Exception {
        while(!exit){
            menuCustomers();
            int choice = getInput();
            actionCustomers(hotel, choice);
        }
    }

    private static void menuCustomers() {
        System.out.println("\nChoose the option: ");
        System.out.println("---------------------");
        System.out.println("0. Back to the main menu");
        System.out.println("1. Create a new customer");
        System.out.println("2. Delete a customer");
        System.out.println("3. List customers");
    }

    private static void actionCustomers(Hotel hotel, int choice) throws Exception {
        switch(choice){
            case 0 -> runMenu();
            case 1 -> createCustomer(hotel);
            case 2 -> deleteCustomer(hotel);
            case 3 -> listCustomers(hotel);
            default -> System.out.println("Unknown error");
        }
    }
    // End submenu

    // Create customer
    private static void createCustomer(Hotel hotel) {
        Customer customer;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the customer's ID: ");
        String id = sc.nextLine();
        // Check if the customer exists
        if(!Customer.customerExists(hotel, id)) {
            System.out.print("Enter the name: ");
            String name = sc.nextLine();
            System.out.print("Enter the Surname: ");
            String surname = sc.nextLine();
            customer = new Customer(name, surname, id);
            // Add it to the hotel
            hotel.addCustomer(customer);
            System.out.println("Customer created. " + customer);
        } else  {
            System.out.println("This customer already exists");
        }
    }

    // Delete customer
    private static void deleteCustomer(Hotel hotel) {
        boolean customerDeleted = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------------");
        System.out.println("   Delete customer   ");
        System.out.println("---------------------");
        System.out.print("enter the customer's ID: ");
        String idCustomerDelete = sc.nextLine();

        for (Customer customer : hotel.getCustomers()) {
            if (customer.getID().equals(idCustomerDelete)) {
                Customer.deleteCustomer(hotel, idCustomerDelete);
                customerDeleted = true;
                System.out.println("Customer has been deleted.");
                break;
            }
        }
        if (!customerDeleted) {
            System.out.println("Customer not found");
        }
    }

    // List customers
    private static void listCustomers(Hotel hotel) {
        System.out.println("-------------------------");
        System.out.println("    List of customers    ");
        System.out.println("-------------------------");

        for (Customer customer : hotel.getCustomers()) {
            System.out.println(customer);
        }
    }





    // Show available rooms in a period entered by the user filtered by types of room
    private static void availableRoomsPeriod(Hotel hotel) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the start date of the period to be checked (YYYY-mm-dd): ");
        LocalDate startPeriod = Reservation.parseDate(sc.nextLine());
        System.out.println("Enter the end date of the period to be checked (YYYY-mm-dd): ");
        LocalDate endPeriod = Reservation.parseDate(sc.nextLine());

        // Show available rooms
        System.out.println("======================================================");
        System.out.println("Rooms available between " + startPeriod + " and " + endPeriod);
        System.out.println("======================================================");
        System.out.println("              Available single rooms                  ");
        System.out.println("======================================================");

        // check if the rooms are in a reservation
        for (Room room : hotel.getRooms()) {
            if (room instanceof SingleRoom) {
                Reservation.roomsPeriodCheck(hotel, startPeriod, endPeriod, room);
            }
        }

        System.out.println("======================================================");
        System.out.println("                 Available double rooms               ");
        System.out.println("======================================================");

        for (Room room : hotel.getRooms()) {
            if (room instanceof DoubleRoom) {
                Reservation.roomsPeriodCheck(hotel, startPeriod, endPeriod, room);
            }
        }

        System.out.println("======================================================");
        System.out.println("                Available suite rooms                 ");
        System.out.println("======================================================");

        for (Room room : hotel.getRooms()) {
            if (room instanceof SuiteRoom) {
                Reservation.roomsPeriodCheck(hotel, startPeriod, endPeriod, room);
            }
        }
    }

    // Show reservations in a period entered by the user
    private static void reservationsPeriod(Hotel hotel) {
        boolean found = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the starting date of the period (YYYY-mm-dd): ");
        LocalDate startPeriod = Reservation.parseDate(sc.nextLine());
        System.out.println("Enter the ending date of the period (YYYY-mm-dd): ");
        LocalDate endPeriod = Reservation.parseDate(sc.nextLine());
        // Iterate over reservations
        if (!hotel.getReservations().isEmpty()) {
            System.out.println("Existing reservations for the period");
            System.out.println("====================================");

            for (Reservation reservation : hotel.getReservations()) {
                // Get reservation entry and exit dates
                LocalDate startingDate = reservation.getEntryDate();
                LocalDate exitDate = reservation.getExitDate();
                if (Reservation.dateInPeriod(startPeriod, endPeriod, startingDate, exitDate)) {
                    // Print true values
                    System.out.println(reservation);
                    found = true;
                }
            }
            // If no reservations have been found
            if (!found) {
                System.out.println("There are no reservations on this period");
            }
        } else {
            System.out.println("There are no reservations in the hotel, contact tech support");
        }

    }

    // Reservations filtered by customer in a period
    private static void reservationsCustomerPeriod(Hotel hotel) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the starting date for the period (YYYY-mm-dd): ");
        LocalDate startPeriod = Reservation.parseDate(sc.nextLine());
        System.out.println("Enter the ending date for the period (YYYY-mm-dd): ");
        LocalDate endPeriod = Reservation.parseDate(sc.nextLine());

        for (Customer customer : hotel.getCustomers()) {
            boolean inTheReservation = false;
            for (Reservation reservation : hotel.getReservations()) {
                LocalDate entry = reservation.getEntryDate();
                LocalDate exit = reservation.getExitDate();

                // If the reservation in the period and is the customer
                if (Reservation.dateInPeriod(startPeriod, endPeriod, entry, exit) &&
                        (reservation.getCustomer().contains(customer))) {
                    inTheReservation = true;
                }
                if (inTheReservation) {
                    System.out.println(reservation);
                }
                // reset boolean value
                inTheReservation = false;
            }
        }
    }
}

