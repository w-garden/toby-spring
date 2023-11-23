package springbook.user.test;

import springbook.user.domain.User;

import java.sql.*;

public class UserDao {
    public UserDao() {
        System.out.println("##############################");
        System.out.println("UserDao 실행");
        System.out.println("java.sql.Connection.java 사용");
        System.out.println("##############################\n");
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }



    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?"
        );
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        ps.close();
        c.close();
        return user;
    }

    public int delete() throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(
                "delete from users"
        );
        int rows = ps.executeUpdate();
        ps.close();
        c.close();
        return rows;

    }
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/spring", "spring", "spring");
        return c;
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserDao dao = new UserDao();
        User user = new User();

        user.setId("user00");
        user.setName("신호철");
        user.setPassword("1234567");
        dao.delete();

        dao.add(user);

        System.out.println("#####  "+user.getId()+" 가입성공 #####");

        User user2 = dao.get(user.getId());
        System.out.println("이름 : " + user2.getName());
        System.out.println("ID   : " + user2.getId());
        System.out.println("PW   : " + user2.getPassword());

        System.out.println("#####  "+user2.getId() + " 조회성공 #####");
    }

}
