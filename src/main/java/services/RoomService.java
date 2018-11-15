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
        return em.createNamedQuery("Employee.findAll", Room.class).getResultList();
    }
}