package services;

import models.Room;

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
}