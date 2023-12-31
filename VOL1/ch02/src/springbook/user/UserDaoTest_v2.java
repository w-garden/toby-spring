package springbook.user;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class UserDaoTest_v2 {
    @Test
    public void addAndGet() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        User user = new User();
        user.setId("spring");
        user.setName("스프링");
        user.setPassword("springNo1");

        dao.add(user);
        assertThat(dao.getCount(), is(1));

        User user2 = dao.get(user.getId());

        assertThat(user2.getName(), is(user.getName()));
        assertThat(user2.getPassword(), is(user.getPassword()));
    }

}
