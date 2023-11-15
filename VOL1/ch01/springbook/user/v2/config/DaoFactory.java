package springbook.user.v2.config;


import springbook.user.connection.ConnectionMaker;
import springbook.user.connection.DConnectionMaker;
import springbook.user.v2.dao.AccountDao;
import springbook.user.v2.dao.UserDao;



public class DaoFactory {

    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    public AccountDao accountDao() {
        return new AccountDao(connectionMaker());
    }


    public ConnectionMaker connectionMaker(){
        return new DConnectionMaker();
    }
}
