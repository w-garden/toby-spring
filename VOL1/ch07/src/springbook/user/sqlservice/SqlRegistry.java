package springbook.user.sqlservice;

/**
 *  public class XmlSqlService implements SqlService, SqlRegistry, SqlReader
 *  위와 같은 XmlSqlService를 단독 클래스로 분리
 */
public interface SqlRegistry {
    void registrySql(String key, String sql);
    String findSql(String key) throws SqlNotFoundException;
}
