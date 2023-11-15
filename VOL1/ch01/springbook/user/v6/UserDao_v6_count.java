package springbook.user.v6;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.connection.CountingConnectionMaker;
import springbook.user.domain.User;
import springbook.user.v6.config.CountingDaoFactory;
import springbook.user.v6.dao.UserDao_v6;

import java.sql.SQLException;

public class UserDao_v6_count {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao_v6 dao = context.getBean("userDao", UserDao_v6.class);

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
