package models;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Room e")
})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String roomNumber;

    public int getId() {
        return id;
    }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
}