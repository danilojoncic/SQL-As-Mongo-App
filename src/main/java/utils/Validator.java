package utils;

import third_perspective.Clauses.*;
import third_perspective.Query;

public class Validator {
    public static Validator instace;

    private Validator() {
    }

    public static Validator getInstance() {
        if (instace == null) {
            instace = new Validator();
        }
        return instace;
    }

    public boolean validate(Query query) {
        boolean fromPresent = false;
        boolean selectPresent = false;

        for (AbsClause allClause : query.getAllClauses()) {
            if (allClause instanceof From) {
                if (!((From) allClause).getArgs().isEmpty()) {
                    fromPresent = true;
                }

            }
            if (allClause instanceof Select) {
                if (!((Select) allClause).getSelectedColumns().isEmpty()) {
                    selectPresent = true;
                }
            }
            if(allClause instanceof Where){
                if(((Where) allClause).getFieldConditionValue().isEmpty()){
                    return false;
                }
            }
            if(allClause instanceof Order_By){
                if(((Order_By) allClause).getArgs().isEmpty()){
                    return false;
                }
            }
        }
        if (selectPresent == false || fromPresent == false) {
            return false;
        }
        return true;
    }
}


