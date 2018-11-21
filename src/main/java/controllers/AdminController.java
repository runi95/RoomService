package controllers;

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
    @Path("create")
    public Room createRoom(@HeaderParam("roomNumber") String roomNumber, @HeaderParam("owner") String owner, @HeaderParam("date") String date) {
        if (roomNumber == null || owner == null) {
            return null;
        }

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setOwnedBy(owner);

        if (date != null) {
            LocalDate localDate = LocalDate.parse(date);
            room.setPublicEndDate(localDate);
        }

        roomService.save(room);

        return room;
    }

    @PUT
    @Path("{id}/update")
    public Room updateRoom(@PathParam("id") String id, @HeaderParam("roomNumber") String roomNumber, @HeaderParam("owner") String owner, @HeaderParam("date") String date) {
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

        if (date != null) {
            LocalDate localDate = LocalDate.parse(date);
            room.setPublicEndDate(localDate);
        }

        roomService.save(room);

        return room;
    }
}
