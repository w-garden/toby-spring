package springbook.user.config;


import springbook.user.connection.ConnectionMaker;
import springbook.user.connection.DConnectionMaker;
import springbook.user.dao.AccountDao;
import springbook.user.dao.UserDao_v4;



public class DaoFactory_v4 {

    public UserDao_v4 userDao() {
        return new UserDao_v4(connectionMaker());
    }

    public AccountDao accountDao() {
        return new AccountDao(connectionMaker());
    }


    public ConnectionMaker connectionMaker(){
        return new DConnectionMaker();
    }
}
