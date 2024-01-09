package springbook;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.dao.UserDao;
import springbook.dao.UserDaoJdbc;
import springbook.domain.Level;
import springbook.domain.User;
import springbook.service.UserService;
import springbook.service.UserServiceImpl;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static springbook.user.UserConst.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.UserConst.MIN_RECCOMEND_FOR_GOLD;

/**
 * @Transactional 애노테이션 사용
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(defaultRollback = false)
//@Transactional
@ContextConfiguration(locations = "/applicationContext_v7.xml")
public class UserServiceTest_v7 {
    @Autowired
    private UserService userService;
    @Autowired
    private UserService testUserService;
    @Autowired
    private UserDaoJdbc userDao;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    MailSender mailSender;

    @Autowired
    ApplicationContext context;
    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "bumjin@naver.com"),
                new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "joytouch@naver.com"),
                new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD - 1),
                new User("madnite11", "이상호", "p4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD, "madnite11@naver.com"),
                new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "green@naver.com")
        );
    }

    @Test
    public void upgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel(), is(Level.SILVER));
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel(), is(Level.GOLD));

        ArgumentCaptor<SimpleMailMessage> mailMessageArg =
                ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender, times(2)).send(mailMessageArg.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
        assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));

    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }

    }

    static class TestUserService extends UserServiceImpl {
        private String id = "madnite11";

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }

        @Override
        public List<User> getAll() {
            for (User user : super.getAll()) {
                super.update(user); //강제로 쓰기를 시도 -> 예외 발생
            }
            return null;
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }

    @Test
    public void upgradeAllOrNothing() {
        userDao.deleteAll();
        for (User user : users) userDao.add(user);
        try {
            this.testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) {
        }

        checkLevelUpgraded(users.get(1), false);
    }

    @Test
    public void advisorAutoProxyCreator() {
        //모든 JDK 다이내믹 프록시 방식으로 만들어지는 프록시는 Proxy 클래스의 서브클래스이다
        assertThat(testUserService, is(java.lang.reflect.Proxy.class));
    }

    @Test(expected = TransientDataAccessResourceException.class)
    public void readOnlyTransactionAttribute(){
        testUserService.getAll();
    }
    @Test(expected = TransientDataAccessResourceException.class)
    public void transactionSync(){
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();

        TransactionStatus txStatus = transactionManager.getTransaction(txDefinition);

        userService.deleteAll();

        userService.add(users.get(0));
        userService.add(users.get(1));

        transactionManager.commit(txStatus);
    }
    @Test
    public void transactionSync_읽기전용_예외_테스트() {
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setReadOnly(true);

        TransactionStatus txStatus = transactionManager.getTransaction(txDefinition);
        userService.deleteAll();

        userService.add(users.get(0));
        userService.add(users.get(1));

        transactionManager.commit(txStatus);
    }

    @Test
    public void transactionSync_롤백_테스트() {
        userService.deleteAll();
        assertThat(userDao.getCount(), is(0));

        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDefinition);

        userService.add(users.get(0));
        userService.add(users.get(1));
        assertThat(userDao.getCount(), is(2));

        transactionManager.rollback(txStatus);

        assertThat(userDao.getCount(), is(0));
    }

    @Test
    @Transactional
    public void transactionSync_테스트_애노테이션_적용() {
        userService.deleteAll();
        userService.add(users.get(0));
        userService.add(users.get(1));
    }


}
