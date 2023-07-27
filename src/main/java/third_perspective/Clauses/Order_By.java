package third_perspective.Clauses;

import java.util.List;

public class Order_By extends Clause {



    private List<String> args;

    public Order_By(List<String> args) {
        super(args);
        this.args = args;
    }

    @Override
    public void addFields(String input) {
        this.args.add(input);

    }

    @Override
    public String Jsonify() {
        StringBuilder sb = new StringBuilder();
        String field = args.get(0);
        sb.append('{');
        sb.append(field);
        sb.append(" : ");
        if (args.size()==2){
            sb.append("-1");
        } else {
        sb.append("1 ");
        }
        sb.append('}');
        return sb.toString();
    }
    public List<String> getArgs() {
        return args;
    }
    public void setArgs(List<String> args) {
        this.args = args;
    }
}
