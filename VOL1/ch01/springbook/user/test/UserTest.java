package springbook.user.test;

import springbook.user.domain.User;
import springbook.user.dao.UserDao;

import java.sql.SQLException;

public class UserTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserDao dao = new UserDao();
        User user = new User();

        user.setId("user00");
        user.setName("신호철");
        user.setPassword("123456");
        int deleteRows=dao.delete();
        System.out.println(deleteRows+" rows 삭제(기존 데이터 삭제)\n");

        dao.add(user);

        System.out.println("#####  "+user.getId()+" 가입성공 #####");

        User user2 = dao.get(user.getId());
        System.out.println("이름 : " + user2.getName());
        System.out.println("ID   : " + user2.getId());
        System.out.println("PW   : " + user2.getPassword());

        System.out.println("#####  "+user2.getId() + " 조회성공 #####");
    }

}
