package springbook.user.dao.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoGet extends UserDao_v1 {
    @Override
    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        return ps;
    }
}
