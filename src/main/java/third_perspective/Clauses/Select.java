package third_perspective.Clauses;


import java.util.List;

public class Select extends Clause {
    private List<String> selectedColumns;

    public Select(List<String> selectedColumns) {
        super(selectedColumns);
        this.selectedColumns = selectedColumns;
    }

    public void addSelectedColumn(String selected){
        this.selectedColumns.add(selected);
    }

    @Override
    public void addFields(String input) {
        this.selectedColumns.add(input);

    }

    @Override
    public String Jsonify() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        int size = selectedColumns.size();
        for (int i = 0; i < size; i++) {
            sb.append(selectedColumns.get(i));
            sb.append(": 1");
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append(" }");
        return sb.toString();
    }


    public List<String> getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(List<String> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }



}
