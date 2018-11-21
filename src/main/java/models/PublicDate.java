package models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "PublicDate.findConflictingDates", query = "SELECT pd FROM PublicDate pd WHERE pd.room.id = :roomId AND pd.publicStartDate <= :date AND pd.publicEndDate >= :date"),
})
public class PublicDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDateTime publicStartDate;

    private LocalDateTime publicEndDate;

    public int getId() {
        return id;
    }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public LocalDateTime getPublicStartDate() { return publicStartDate; }
    public void setPublicStartDate(LocalDateTime publicStartDate) { this.publicStartDate = publicStartDate; }

    public LocalDateTime getPublicEndDate() { return publicEndDate; }
    public void setPublicEndDate(LocalDateTime publicEndDate) { this.publicEndDate = publicEndDate; }
}
