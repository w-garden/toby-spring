package springbook.user;

import springbook.user.sqlservice.ConcurrentHashMapSqlRegistry;
import springbook.user.sqlservice.updatable.UpdatableSqlRegistry;


public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        return new ConcurrentHashMapSqlRegistry();


    }
}