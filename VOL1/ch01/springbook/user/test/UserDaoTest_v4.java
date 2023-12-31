package springbook.user.test;

import springbook.user.config.DaoFactory_v4;
import springbook.user.dao.UserDao_v4;
import springbook.user.domain.User;

import java.sql.SQLException;


public class UserDaoTest_v4 {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao_v4 dao = new DaoFactory_v4().userDao();
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
