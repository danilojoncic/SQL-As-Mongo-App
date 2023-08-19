package third_perspective.Clauses;

import java.util.List;

public class Clause extends AbsClause {
    List<String> list;

    public Clause(List<String> list) {
        this.list = list;
    }

    public void addFields(String input){
        list.add(input);
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String jsonify() {
        return null;
    }
}
