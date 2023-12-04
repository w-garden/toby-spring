package springbook.user.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 1. workWithStatementStrategy, executeSql, executeVarargsSql
 * 하나의 목적을 위해 서로 긴밀하게 연관되어 동작하기 때문에 한 군데 모았다.
 */
public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void executeSql(final String query) throws SQLException {
        workWithStatementStrategy(
                c -> c.prepareStatement(query)
        );
    }

    public void executeSql(final String ...str) throws SQLException {
        workWithStatementStrategy(
                c -> {
                    PreparedStatement ps = c.prepareStatement(str[0]);
                    ps.setString(1, str[1]);
                    ps.setString(2, str[2]);
                    ps.setString(3, str[3]);
                    return ps;
                }
        );
    }

}
