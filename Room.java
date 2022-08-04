public class Room {
    private int roomNumber;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    // Check if a room exists
    static boolean roomExists(int roomNumber, Hotel hotel) {
        boolean exists = false;
        for (Room room : hotel.getRooms()) {
            if (room.getRoomNumber() == roomNumber) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    // Delete an existing room
    static void deleteRoom(Hotel hotel, int roomNumber) {
        for (Room room : hotel.getRooms()) {
            if (room.getRoomNumber() == roomNumber) {
                hotel.deleteRoom(room);
                break;
            }
        }
    }
}
