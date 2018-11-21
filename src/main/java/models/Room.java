package models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NamedQueries({
        @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r"),
        @NamedQuery(name = "Room.findById", query = "SELECT r FROM Room r WHERE r.id = :id")
})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String ownedBy;

    private String roomNumber;

    private LocalDate publicEndDate;

    public int getId() {
        return id;
    }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getOwnedBy() { return ownedBy; }
    public void setOwnedBy(String ownedBy) { this.ownedBy = ownedBy;}

    public LocalDate getPublicEndDate() { return publicEndDate; }
    public void setPublicEndDate(LocalDate publicEndDate) { this.publicEndDate = publicEndDate; }

    public void makeRoomPublicUntil(LocalDate endDate) {
        setPublicEndDate(endDate);
    }
}