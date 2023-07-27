package third_perspective;

import third_perspective.Clauses.AbsClause;
import third_perspective.Clauses.Clause;

import java.util.List;

public class Query extends AbsClause {
   private List<AbsClause> allClauses;

    public Query(List<AbsClause> allClauses) {
        this.allClauses = allClauses;
    }

    //ovo dolje mi omogucava da query ima instancu svoje klase kao dio svoje liste
    public void addClause(AbsClause clause){
        this.allClauses.add(clause);
    }
    public List<AbsClause> getAllClauses() {
        return allClauses;
    }

    public void setAllClauses(List<AbsClause> allClauses) {
        this.allClauses = allClauses;
    }

}
