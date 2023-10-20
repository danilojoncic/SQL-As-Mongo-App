import org.bson.Document;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import third_perspective.AdapterMDB;
import utils.Parser;

import java.util.List;

public class ConversionTest {

    String sql1 = "select * from employees";
    String sql2 = "select first_name last_name salary from employees where salary >= 10000";
    String sql3 = "select last_name department_id from employees where last_name like $S and department_id > 50";
    String sql4 = "select first_name salary department_id department_name from employees join departments using depratments where salary > 20000 or department_id = 20";
    String sql5 = "select department_name location_id street_address from departments join locations using location_id";

    String sl1Result = "[Document{{employees=1}}, Document{{}}]";

    String sl2Result = "[Document{{employees=1}}, Document{{first_name=1, last_name=1, salary=1}}, Document{{salary=Document{{$gte=10000}}}}]";

    String sl3Result = "[Document{{employees=1}}, Document{{last_name=1, department_id=1}}, Document{{$and=[Document{{last_name=Document{{$regex=$S}}}}, Document{{department_id=Document{{$gt=50}}}}]}}]";

    String sl5Result = "[Document{{departments=1, join=1, locations=1, using=1, location_id=1}}, Document{{department_name=1, location_id=1, street_address=1}}]";
    List<Document> docs;
    @Test
    public void createdDocumentListTest(){
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql1));
        Assertions.assertNotNull(docs);
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql2));
        Assertions.assertNotNull(docs);
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql3));
        Assertions.assertNotNull(docs);
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql4));
        Assertions.assertNotNull(docs);
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql5));
        Assertions.assertNotNull(docs);
    }
    //
    @Test
    public void createdDocumentStringTest(){
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql1));
        Assertions.assertEquals(sl1Result,docs.toString());
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql2));
        Assertions.assertNotNull(sl2Result,docs.toString());
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql3));
        Assertions.assertNotNull(sl3Result,docs.toString());
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql5));
        Assertions.assertNotNull(sl5Result,docs.toString());
    }
    @Test
    public void query1Test() {
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql1));
        Object[] strings = docs.get(0).keySet().toArray();
        Assertions.assertEquals("employees", (String) strings[0]);
    }
    @Test
    public void query2Test(){
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql2));
        Object[] strings2 = docs.get(0).keySet().toArray();
        Assertions.assertAll(
                ()->Assertions.assertEquals("employees",(String) strings2[0])
        );

        Object[] strings3 = docs.get(1).keySet().toArray();
        Assertions.assertAll(
                ()->Assertions.assertEquals("first_name",(String) strings3[0]),
                ()->Assertions.assertEquals("last_name",(String) strings3[1]),
                ()->Assertions.assertEquals("salary",(String) strings3[2])
        );
        Object[] strings4 = docs.get(2).keySet().toArray();
        Assertions.assertEquals("salary",(String)strings4[0]);
    }

    @Test
    public void query3Test(){
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql3));
        Object[] strings2 = docs.get(0).keySet().toArray();
        Assertions.assertAll(
                ()->Assertions.assertEquals("employees",(String) strings2[0])
        );

        Object[] strings3 = docs.get(1).keySet().toArray();
        Assertions.assertAll(
                ()->Assertions.assertEquals("last_name",(String) strings3[0]),
                ()->Assertions.assertEquals("department_id",(String) strings3[1])
        );
        Object[] strings4 = docs.get(2).keySet().toArray();
        Assertions.assertEquals("$and",(String)strings4[0]);
    }

    @Test
    public void query4Test(){
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql4));
        Object[] strings2 = docs.get(0).keySet().toArray();
        Assertions.assertAll(
                ()->Assertions.assertEquals("employees",(String) strings2[0]),
                ()->Assertions.assertEquals("departments",(String) strings2[2])
        );

        Object[] strings3 = docs.get(1).keySet().toArray();
        Assertions.assertAll(
                ()->Assertions.assertEquals("first_name",(String) strings3[0]),
                ()->Assertions.assertEquals("salary",(String) strings3[1]),
                ()->Assertions.assertEquals("department_id",(String) strings3[2]),
                ()->Assertions.assertEquals("department_name",(String) strings3[3])
        );
        Object[] strings4 = docs.get(2).keySet().toArray();
        Assertions.assertEquals("$or",(String)strings4[0]);
    }
    @Test
    public void query5Tess(){
        docs = AdapterMDB.getInstanceForTest().generateMongoDBQuery(Parser.getInstance().createFromString(sql5));
        Object[] strings2 = docs.get(0).keySet().toArray();
        Assertions.assertAll(
                ()->Assertions.assertEquals("departments",(String) strings2[0]),
                ()->Assertions.assertEquals("locations",(String) strings2[2]),
                ()->Assertions.assertEquals("location_id",(String) strings2[4])
        );

        Object[] strings3 = docs.get(1).keySet().toArray();
        Assertions.assertAll(
                ()->Assertions.assertEquals("department_name",(String) strings3[0]),
                ()->Assertions.assertEquals("location_id",(String) strings3[1]),
                ()->Assertions.assertEquals("street_address",(String) strings3[2])
        );
    }
}
