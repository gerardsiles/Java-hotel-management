public class Customer {
    private String name;
    private String surname;
    private String ID;

    public Customer(String name, String surname, String ID) {
        this.name = name;
        this.surname = surname;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    // Class methods
    // Check if customer exists
    static boolean customerExists (Hotel hotel, String ID) {
        boolean exists = false;
        for (Customer customer : hotel.getCustomers()) {
            if (customer.getID().equals(ID)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    // Delete a customer
    static void deleteCustomer(Hotel hotel, String ID) {
        for (Customer customer : hotel.getCustomers()) {
            hotel.deleteCustomer(customer);
            break;
        }
    }

    @Override
    public String toString() {
        return "Customer details: " + this.surname + ", " + this.name + ", ID: " + this.ID;
    }
}
