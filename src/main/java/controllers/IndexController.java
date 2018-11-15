package controllers;

import models.Room;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;
import services.RoomService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.logging.Logger;

@Path("/")
@ApplicationScoped
@Transactional
public class IndexController {

    @Inject
    @ConfigurationValue("some.string.property")
    private String stringProperty;

    @Inject
    private RoomService service;

    private static final Logger LOG = Logger.getLogger(IndexController.class.getName());

    @GET
    @Path("all")
    @Produces("application/json")
    public List<Room> get() {
        return service.getAll();
    }

    @GET
    @Path("str")
    @Produces("plain/text")
    public String getStr() {
        return stringProperty;
    }

    @GET
    @Path("isPublic/{id}")
    @Produces("application/json")
    public Room isRoomPublic(@PathParam("id") String id) {
        Integer parsedID = null;

        try {
            parsedID = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            LOG.warning("Could not parse " + id + " to int.");

            // e.printStackTrace();
        }

        if (parsedID == null) {
            return null;
        }

        Room room = null;

        try {
            room = service.getRoom(parsedID);
        } catch (NoResultException e) {
            LOG.warning("Could not find a Room with the given id " + id);

            // e.printStackTrace();
        }

        return room;
    }
}