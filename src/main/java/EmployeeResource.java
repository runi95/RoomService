import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@RequestScoped
@Transactional
public class EmployeeResource {

    @GET
    @Path("all")
    @Produces("application/json")
    public List<Employee> get() {
        return service.getAll();
    }

    @GET
    @Path("remWithRollback")
    @Produces("application/json")
    public String remTx() {
        service.remTx(5);
        return "ok";
    }

    @GET
    @Path("remWithoutRollback")
    @Produces("application/json")
    @Transactional(dontRollbackOn = {IllegalStateException.class})
    public String remTxWithNoRollbackOnISE() {
        service.remTx(3);
        return "ok";
    }

    @GET
    @Path("rollbackMsg")
    @Produces("text/plain")
    public int getRollBackMsg() {

        return service.getRollbackMsg().size();
    }

    @GET
    @Path("commitMsg")
    @Produces("text/plain")
    public int getCommitMsg() {

        return service.getCommitMsg().size();
    }

    @Inject
    private EmployeeService service;
}