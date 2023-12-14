package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JdbcTemplate
 */
public class UserDao_v7 {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }


    public void add(final User user) {
        this.jdbcTemplate.update("insert into users(id,name, password) values (?,?,?)"
                , user.getId()
                , user.getName()
                , user.getPassword());
    }


    public void deleteAll() {
        //this.jdbcTemplate.update(con -> con.prepareStatement("delete from users"));
        this.jdbcTemplate.update("delete from users");
    }


    public User get(String id) throws SQLException {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?"
                , new Object[]{id}
                , new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));
                        return user;
                    }
                });

    }

    public int getCount() throws SQLException {
       /* return this.jdbcTemplate.query(new PreparedStatementCreator() {
                                           @Override
                                           public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                                               return con.prepareStatement("select count(*) from users");
                                           }
                                       }, new ResultSetExtractor<Integer>() {
                                           @Override
                                           public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                                               rs.next();
                                               return rs.getInt(1);
                                           }
                                       }
        ); */
        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }

}
