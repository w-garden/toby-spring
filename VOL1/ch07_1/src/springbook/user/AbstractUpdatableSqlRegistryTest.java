package springbook.user;

import org.junit.Before;
import org.junit.Test;
import springbook.user.sqlservice.ConcurrentHashMapSqlRegistry;
import springbook.user.sqlservice.SqlNotFoundException;
import springbook.user.sqlservice.updatable.SqlUpdateFailureException;
import springbook.user.sqlservice.updatable.UpdatableSqlRegistry;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public abstract class AbstractUpdatableSqlRegistryTest {
    protected UpdatableSqlRegistry sqlRegistry;

    @Before
    public void setUp() {
        sqlRegistry = createUpdatableSqlRegistry();
        sqlRegistry.registrySql("KEY1", "SQL1");
        sqlRegistry.registrySql("KEY2", "SQL2");
        sqlRegistry.registrySql("KEY3", "SQL3");
    }

    protected abstract UpdatableSqlRegistry createUpdatableSqlRegistry(); //서브 클래스에서 이를 구현하면 된다.
    //서브클래스에서 접근이 가능하도록 protected 로 한다.
    protected void checkFindResult(String expected1, String expected2, String expected3) {
        assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
        assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
        assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
    }

    @Test
    public void find() {
        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    @Test(expected = SqlNotFoundException.class)
    public void unknownKey() {
        sqlRegistry.findSql("SQL9999!");
    }

    @Test
    public void updateSingle() {
        sqlRegistry.updateSql("KEY2", "Modified2");
        checkFindResult("SQL1", "Modified2", "SQL3");
    }

    @Test
    public void updateMulti() {
        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY3", "Modified3");
        sqlRegistry.updateSql(sqlmap);
        checkFindResult("Modified1", "SQL2", "Modified3");
    }

    @Test(expected = SqlUpdateFailureException.class)
    public void updateWithNoExistingKey(){
        sqlRegistry.updateSql("SQL999!","Modified2");
    }
}
