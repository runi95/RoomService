package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r"),
        @NamedQuery(name = "Room.findById", query = "SELECT r FROM Room r WHERE r.id = :id")
})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ownedBy;

    private String roomNumber;

    private LocalDateTime publicStartDate;

    private LocalDateTime publicEndDate;

    public int getId() {
        return id;
    }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getOwnedBy() { return ownedBy; }
    public void setOwnedBy(String ownedBy) { this.ownedBy = ownedBy;}

    public LocalDateTime getPublicStartDate() { return publicStartDate; }
    public void setPublicStartDate(LocalDateTime publicStartDate) { this.publicStartDate = publicStartDate; }

    public LocalDateTime getPublicEndDate() { return publicEndDate; }
    public void setPublicEndDate(LocalDateTime publicEndDate) { this.publicEndDate = publicEndDate; }

    public void makeRoomPublicUntil(LocalDateTime endDate) {
        setPublicEndDate(endDate);
    }

    public void makeRoomPublicFromTo(LocalDateTime startDate, LocalDateTime endDate) {
        setPublicStartDate(startDate);
        setPublicEndDate(endDate);
    }
}