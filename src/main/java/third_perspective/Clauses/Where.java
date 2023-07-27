package third_perspective.Clauses;

import java.util.ArrayList;
import java.util.List;


public class Where extends Clause {
    private List<String> fieldConditionValue;

    public Where(List<String> fieldConditionValue) {
        super(fieldConditionValue);
        this.fieldConditionValue = fieldConditionValue;
    }

    public List<String> getFieldConditionValue() {
        return fieldConditionValue;
    }

    public void setFieldConditionValue(List<String> fieldConditionValue) {
        this.fieldConditionValue = fieldConditionValue;
    }

    @Override
    public void addFields(String input) {
        this.fieldConditionValue.add(input);
    }

/*
       if (fieldConditionValue.contains("and") || fieldConditionValue.contains("or")) {
          List<String> fv1 = new ArrayList<>();
           List<String> fv2 = new ArrayList<>();
          int i;
           for (i = 0; i < fieldConditionValue.size() &&
                   !(fieldConditionValue.get(i).equals("and") || fieldConditionValue.get(i).equals("or")); i++) {
               fv1.add(fieldConditionValue.get(i));
            }
 */
    @Override
    public String Jsonify() {
        //souls of mischief song: '93 til Infinity
        int index = 0;
        List<String> conditions = new ArrayList<>();
        while(index < fieldConditionValue.size()){
            List<String> fv = new ArrayList<>();
            while(index < fieldConditionValue.size() &&
                    !(fieldConditionValue.get(index).equals("and") || fieldConditionValue.get(index).equals("or"))){
                fv.add(fieldConditionValue.get(index));
                index++;
            }
            conditions.add(subJsonify(fv));
            if(index < fieldConditionValue.size()){
                conditions.add(fieldConditionValue.get(index));
                index++;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        if(conditions.size() > 1){
            stringBuilder.append("{ $").append(conditions.get(1)).append(": [");
            for(int i = 0; i < conditions.size(); i += 2){
                stringBuilder.append(conditions.get(i));
                if(i + 2 < conditions.size()){
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append("] }");
        }else{
            stringBuilder.append(conditions.get(0));
        }
        return stringBuilder.toString();
    }
    private String subJsonify(List<String> list){
        StringBuilder sb = new StringBuilder();
        String field = list.get(0);
        String condition = list.get(1);
        String value = list.get(2);
        sb.append("{");
        sb.append(field).append(": ");
        //like za wildcard %
        //{ $and: [{first_name: { $regex: '.*S' }}, {salary: 7000}] }
        //[Document{{employees=1}}, Document{{first_name=1, salary=1}},
        // Document{{$and=[Document{{first_name=Document{{$regex=.*S}}}}, Document{{salary=7000}}]}}]
        //
        //{salary: 7000}
        //[Document{{employees=1}}, Document{{first_name=1, salary=1}}, Document{{salary=7000}}]
        //mali bag za slucaj da se zove select nesto from nesto where nesto = nesto (= je prob)
        if(condition.equals("=")){
            sb.append(value);
        } else if(condition.equals("like")){
            //Fireship yt za regex :)
            String regexPattern = value.replace("%", ".*").replace("_",".");
            sb.append("{ $regex: '").append(regexPattern).append("' }");
        }else if(condition.equals(">")){
            sb.append("{ $gt: ").append(value).append(" }");
        } else if(condition.equals("<")){
            sb.append("{ $lt: ").append(value).append(" }");
        }else if(condition.equals(">=")){
            sb.append("{ $gte: ").append(value).append(" }");
        } else if(condition.equals("<=")){
            sb.append("{ $lte: ").append(value).append(" }");
        } else if(condition.equals("in")){
            sb.append("{ $in: ").append(value).append(" }");
        } else {
            sb.append(value);
        }
        sb.append("}");
        return sb.toString();
    }
}
