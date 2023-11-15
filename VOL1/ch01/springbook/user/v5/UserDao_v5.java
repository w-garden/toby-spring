package springbook.user.v5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;
import springbook.user.v5.config.DaoFactory_v5;

import java.sql.SQLException;


public class UserDao_v5 {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context =  new AnnotationConfigApplicationContext(DaoFactory_v5.class);

        springbook.user.v5.dao.UserDao_v5 dao = context.getBean("userDao", springbook.user.v5.dao.UserDao_v5.class);

        int deleteRows=dao.delete();

        System.out.println(deleteRows+" 개 데이터 삭제성공!!!");


        User user = new User();
        user.setId("shc729");
        user.setName("신호철");
        user.setPassword("1234");


        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println("user2.getName() : " + user2.getName());
        System.out.println("user2.getPassword) : " + user2.getPassword());

        System.out.println(user2.getId() + " 조회성공");


    }


}
