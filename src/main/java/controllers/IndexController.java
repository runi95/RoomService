package controllers;

import models.Room;
import services.RoomService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/")
@ApplicationScoped
@Transactional
public class IndexController {

    @Inject
    private RoomService service;

    @GET
    @Path("all")
    @Produces("application/json")
    public List<Room> get() {
        return service.getAll();
    }
}