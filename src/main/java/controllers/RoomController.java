package controllers;

import models.Room;
import services.RoomService;
import utils.Parser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Path("/room")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class RoomController {

    @Inject
    private RoomService roomService;

    private static final Logger LOG = Logger.getLogger(RoomController.class.getName());

    @GET
    @Path("all")
    public List<Room> get() {
        return roomService.getAll();
    }

    @GET
    @Path("isPublic/{id}")
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
            room = roomService.getRoom(parsedID);
        } catch (NoResultException e) {
            LOG.warning("Could not find a Room with the given id " + id);

            // e.printStackTrace();
        }

        return room;
    }

    @PUT
    @Path("setPublic/{id}")
    public Room setRoomAsPublic(@PathParam("id") String id, @HeaderParam("email") String email, @HeaderParam("startDate") String startDate, @HeaderParam("endDate") String endDate) {
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
            room = roomService.getRoom(parsedID);
        } catch (NoResultException e) {
            LOG.warning("Could not find a Room with the given id " + id);

            // e.printStackTrace();
        }

        if (room == null) {
            return null;
        }

        if (!room.getOwnedBy().equals(email)) {
            return null;
        }

        LocalDateTime localStartDate = Parser.parseDate("start", startDate);
        LocalDateTime localEndDate = Parser.parseDate("end", endDate);

        if (localStartDate == null || localEndDate == null) {
            return null;
        }

        room.makeRoomPublicFromTo(localStartDate, localEndDate);
        roomService.save(room);

        return room;
    }
}