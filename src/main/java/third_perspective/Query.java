package third_perspective;

import third_perspective.Clauses.AbsClause;
import third_perspective.Clauses.Clause;
import third_perspective.Clauses.Composite;

import java.util.List;

public class Query extends AbsClause {
   private List<Composite> allClauses;

    public Query(List<Composite> allClauses) {
        this.allClauses = allClauses;
    }

    //ovo dolje mi omogucava da query ima instancu svoje klase kao dio svoje liste
    public void addClause(Composite clause){
        this.allClauses.add(clause);
    }
    public List<Composite> getAllClauses() {
        return allClauses;
    }

    public void setAllClauses(List<Composite> allClauses) {
        this.allClauses = allClauses;
    }

    @Override
    public String jsonify() {
        return null;
    }
}
