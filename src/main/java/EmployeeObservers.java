import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

import org.jboss.logging.Logger;

@ApplicationScoped
public class EmployeeObservers {

    void processTxFailure(@Observes(during = TransactionPhase.AFTER_FAILURE) Employee emp) {
        String msg = "*** An error occurred and deletion of emp # " + emp.getId() + " was roll-backed";
        LOG.info(msg);
        service.addRollbackMsg(msg);
    }

    void processTxSuccess(@Observes(during = TransactionPhase.AFTER_SUCCESS) Employee emp) {
        String msg = "*** Deletion of emp # " + emp.getId() + " was committed";
        LOG.info(msg);
        service.addCommitMsg(msg);
    }

    final static Logger LOG = Logger.getLogger(EmployeeObservers.class);

    @Inject
    EmployeeService service;
}