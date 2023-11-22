package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoAdd extends UserDao_v1 {
    @Override
    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps =  c.prepareStatement("insert into users(id, name, password) values(?,?,?)");

        return ps;
    }
}
