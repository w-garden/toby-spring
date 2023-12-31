package springbook.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import springbook.user.dao.v3.UserDao_v3;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class UserDaoTest_v3 {
    UserDao_v3 dao;
    private User user1;
    private User user2;
    private User user3;
    @Before
    public void setUp() throws Exception {
        this.user1 = new User("user1", "사용자1", "11111");
        this.user2 = new User("user2", "사용자2", "22222");
        this.user3 = new User("user3", "사용자3", "33333");
        dao = new UserDao_v3();
        dao.setDataSource(new SingleConnectionDataSource("jdbc:mysql://localhost/testdb"
                , "spring"
                , "spring"
                , true));

        dao.deleteAll();
    }
    @Test
    public void addAndGet() throws SQLException {
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));

    }

    @Test
    public void count() throws SQLException {
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {
        assertThat(dao.getCount(),is(0));

        dao.get("unknown_id");
    }

}