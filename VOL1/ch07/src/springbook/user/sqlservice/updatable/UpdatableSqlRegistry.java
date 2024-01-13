package springbook.user.sqlservice.updatable;

import springbook.user.sqlservice.SqlRegistry;

import java.util.Map;

/**
 * SQL저장소에 담긴 SQL 정보를 update 하기 위한 인터페이스
 */
public interface UpdatableSqlRegistry extends SqlRegistry {
    public void updateSql(String key, String sql) throws SqlUpdateFailureException;
    public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException;
}
