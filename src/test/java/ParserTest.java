import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import third_perspective.Clauses.*;
import third_perspective.Query;
import utils.Parser;

public class ParserTest {
    String sql1 = "select * from employees";
    String sql2 = "select first_name last_name salary from employees where salary >= 10000";
    String sql3 = "select last_name department_id from employees where last_name like $S and department_id > 50";
    String sql4 = "select first_name salary department_id department_name from employees join departments using depratments where salary > 20000 or department_id = 20";
    String sql5 = "select department_name location_id street_address from departments join locations using location_id";

    @Test
    public void queryObjectNotNullTest() {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(prepareQuery(sql1)),
                () -> Assertions.assertNotNull(prepareQuery(sql3)),
                () -> Assertions.assertNotNull(prepareQuery(sql5))
        );
    }

    @Test
    public void queryObjectClauseNumberTest() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, prepareQuery(sql1).getAllClauses().size()),
                () -> Assertions.assertEquals(3, prepareQuery(sql2).getAllClauses().size()),
                () -> Assertions.assertEquals(3, prepareQuery(sql3).getAllClauses().size()),
                () -> Assertions.assertEquals(3, prepareQuery(sql4).getAllClauses().size()),
                () -> Assertions.assertEquals(2, prepareQuery(sql5).getAllClauses().size())
        );
    }

    @Test
    public void clauseObjectTypeTest() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(prepareQuery(sql1).getAllClauses().get(0) instanceof Select),
                () -> Assertions.assertTrue(prepareQuery(sql1).getAllClauses().get(1) instanceof From),
                () -> Assertions.assertTrue(prepareQuery(sql4).getAllClauses().get(0) instanceof Select),
                () -> Assertions.assertTrue(prepareQuery(sql4).getAllClauses().get(1) instanceof From),
                () -> Assertions.assertTrue(prepareQuery(sql4).getAllClauses().get(2) instanceof Where)
        );
    }

    @Test
    public void clauseObjectContentsTest() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(((Select) prepareQuery(sql1).getAllClauses().get(0)).getSelectedColumns().get(0), "*"),
                () -> Assertions.assertEquals(((From) prepareQuery(sql1).getAllClauses().get(1)).getArgs().get(0), "employees")
        );
        Assertions.assertAll(
                () -> Assertions.assertEquals(((Select) prepareQuery(sql4).getAllClauses().get(0)).getSelectedColumns().get(0), "first_name"),
                () -> Assertions.assertEquals(((Select) prepareQuery(sql4).getAllClauses().get(0)).getSelectedColumns().get(1), "salary"),
                () -> Assertions.assertEquals(((Select) prepareQuery(sql4).getAllClauses().get(0)).getSelectedColumns().get(2), "department_id"),
                () -> Assertions.assertEquals(((Select) prepareQuery(sql4).getAllClauses().get(0)).getSelectedColumns().get(3), "department_name"),
                () -> Assertions.assertEquals(((From) prepareQuery(sql4).getAllClauses().get(1)).getArgs().get(0), "employees"),
                () -> Assertions.assertEquals(((From) prepareQuery(sql4).getAllClauses().get(1)).getArgs().get(2), "departments"),
                () -> Assertions.assertEquals(((Where) prepareQuery(sql4).getAllClauses().get(2)).getList().get(0), "salary"),
                () -> Assertions.assertEquals(((Where) prepareQuery(sql4).getAllClauses().get(2)).getList().get(1), ">"),
                () -> Assertions.assertEquals(((Where) prepareQuery(sql4).getAllClauses().get(2)).getList().get(2), "20000"),
                () -> Assertions.assertEquals(((Where) prepareQuery(sql4).getAllClauses().get(2)).getList().get(3), "or"),
                () -> Assertions.assertEquals(((Where) prepareQuery(sql4).getAllClauses().get(2)).getList().get(4), "department_id"),
                () -> Assertions.assertEquals(((Where) prepareQuery(sql4).getAllClauses().get(2)).getList().get(5), "="),
                () -> Assertions.assertEquals(((Where) prepareQuery(sql4).getAllClauses().get(2)).getList().get(6), "20")
        );
    }

    public Query prepareQuery(String string) {
        return Parser.getInstance().createFromString(string);
    }
}
