package springbook.user.v3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.v3.config.CountingDaoFactory;
import springbook.user.domain.User;
import springbook.user.connection.CountingConnectionMaker;
import springbook.user.v3.dao.UserDao;

import java.sql.SQLException;

public class UserDaoConnectionMaker {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("wgarden");
        user.setName("호처리");
        user.setPassword("7777");
        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println("user2.getName() : " + user2.getName());
        System.out.println("user2.getPassword) : " + user2.getPassword());

        System.out.println(user2.getId() + " 조회성공");
        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter : " + ccm.getCounter());
    }
}
