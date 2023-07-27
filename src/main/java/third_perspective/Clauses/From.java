package third_perspective.Clauses;
import java.util.List;

public class From extends Clause {
    private List<String> args;

    public From(List<String> args) {
        super(args);
        this.args = args;
    }


    @Override
    public void addFields(String input) {
        this.args.add(input);
    }
    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public String Jsonify() {
        //Jsonify modifikovati da radi sa join klauzom
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (String arg : args) {
            sb.append(arg);
            sb.append(": 1 ,");
        }
        sb.append("}");
        return sb.toString();
    }

}
