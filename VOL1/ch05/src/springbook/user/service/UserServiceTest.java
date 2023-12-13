package springbook.user.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.dao.UserDao;
import springbook.domain.Level;
import springbook.domain.User;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Before
    public void setUp() {
        ApplicationContext context = new GenericXmlApplicationContext("test-applicationContext.xml");
        this.userService = context.getBean("userService", UserService.class);
//        this.dao = context.getBean("userDao", UserDao.class);
    }
    @Test
    public void bean(){
        Assert.assertThat(this.userService, is(notNullValue()));

    }
}
