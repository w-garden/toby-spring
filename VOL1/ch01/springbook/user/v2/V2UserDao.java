package springbook.user.v2;

import springbook.user.connection.ConnectionMaker;
import springbook.user.connection.DConnectionMaker;
import springbook.user.v2.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;


public class V2UserDao {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao dao = new UserDao(connectionMaker);
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
