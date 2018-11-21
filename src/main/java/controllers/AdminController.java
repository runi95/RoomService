package controllers;

import models.PublicDate;
import models.Room;
import services.RoomService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.logging.Logger;

@Path("/room/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class AdminController {

    @Inject
    private RoomService roomService;

    private static final Logger LOG = Logger.getLogger(AdminController.class.getName());

    @POST
    public Room createRoom(@HeaderParam("roomNumber") String roomNumber, @HeaderParam("owner") String owner) {
        if (roomNumber == null || owner == null) {
            return null;
        }

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setOwnedBy(owner);

        roomService.save(room);

        return room;
    }

    @PUT
    @Path("{id}")
    public Room updateRoom(@PathParam("id") String id, @HeaderParam("roomNumber") String roomNumber, @HeaderParam("owner") String owner) {
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

        Room room = roomService.getRoom(parsedID);
        if (room == null) {
            return null;
        }

        if (roomNumber != null) {
            room.setRoomNumber(roomNumber);
        }

        if (owner != null) {
            room.setOwnedBy(owner);
        }

        roomService.save(room);

        return room;
    }

    @PUT
    @Path("{id}/addPublicDate")
    public Room addPublicDate(@PathParam("id") String id, @HeaderParam("startDate") String startDate, @HeaderParam("endDate") String endDate) {
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

        Room room = roomService.getRoom(parsedID);
        if (room == null) {
            return null;
        }

        if (startDate == null || endDate == null) {
            return null;
        }

        LocalDate localStartDate = LocalDate.parse(startDate);
        if (localStartDate == null) {
            return null;
        }

        LocalDate localEndDate = LocalDate.parse(startDate);
        if (localEndDate == null) {
            return null;
        }

        PublicDate publicDate = new PublicDate();
        publicDate.setPublicStartDate(localStartDate);
        publicDate.setPublicEndDate(localEndDate);

        room.addPublicDate(publicDate);
        roomService.save(room);

        return room;
    }

    @DELETE
    @Path("{id}")
    public boolean deleteRoom(@PathParam("id") String id) {
        Integer parsedID = null;

        try {
            parsedID = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            LOG.warning("Could not parse " + id + " to int.");

            // e.printStackTrace();
        }

        if (parsedID == null) {
            return false;
        }

        Room room = roomService.getRoom(parsedID);
        if (room == null) {
            return false;
        }

        roomService.delete(room);

        return true;
    }
}
