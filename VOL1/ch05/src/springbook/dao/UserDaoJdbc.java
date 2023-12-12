package springbook.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.domain.Level;
import springbook.domain.User;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    private RowMapper<User> userMapper =
            (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setLevel(Level.valueOf(rs.getInt("level")));
                user.setLogin(rs.getInt("login"));
                user.setRecommend(rs.getInt("recommend"));
                return user;
            };

    @Override
    public void add(final User user) {
        this.jdbcTemplate.update("insert into users(id,name, password, level, login, recommend) values (?,?,?,?,?,?)"
                , user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                "update users set name = ?, password = ?, level = ?, login = ?, recommend = ? where id = ? ",
                user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    @Override
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, this.userMapper);
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
    }


}
