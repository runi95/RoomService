package services;

import models.PublicDate;
import models.Room;

import java.time.LocalDate;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class RoomService {

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    public List<Room> getAll() {
        return em.createNamedQuery("Room.findAll", Room.class).getResultList();
    }

    public Room getRoom(int id) { return em.createNamedQuery("Room.findById", Room.class).setParameter("id", id).getSingleResult(); }

    public void save(Room room) { em.persist(room); }

    public void delete(Room room) { em.remove(room); }

    public boolean datesOverlap(Room room, LocalDate startDate, LocalDate endDate) {
        List<PublicDate> publicDateList = em.createNamedQuery("PublicDate.checkDateRange", PublicDate.class).setParameter("roomId", room.getId()).setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();

        return !publicDateList.isEmpty();
    }
}