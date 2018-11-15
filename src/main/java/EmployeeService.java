import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EmployeeService {

    public List<Employee> getAll() {
        return em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }

    public void remTx(int failOn) {
        resetMsgLists();
        for (Integer i = 1; i < 9; i++) {
            Employee employee = em.find(Employee.class, i);
            empvt.fire(employee);
            em.remove(employee);
            if (i >= failOn) {
                throw new IllegalStateException("*** On purpose failure during removal to test Rollback *** ");
            }
        }
    }

    public void resetMsgLists() {
        commitMsg.clear();
        rollbackMsg.clear();
    }

    public void addCommitMsg(String msg) {
        commitMsg.add(msg);
    }

    public void addRollbackMsg(String msg) {
        rollbackMsg.add(msg);
    }

    public List<String> getCommitMsg() {
        return commitMsg;
    }

    public List<String> getRollbackMsg() {
        return rollbackMsg;
    }

    @Inject
    Event<Employee> empvt;

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    private List<String> commitMsg = new ArrayList<>();

    private List<String> rollbackMsg = new ArrayList<>();
}