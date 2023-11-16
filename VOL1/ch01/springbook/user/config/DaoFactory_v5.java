package springbook.user.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.user.connection.ConnectionMaker;
import springbook.user.dao.AccountDao;
import springbook.user.dao.UserDao_v5;
import springbook.user.connection.DConnectionMaker;


@Configuration
public class DaoFactory_v5 {

    @Bean
    public UserDao_v5 userDao() {
        UserDao_v5 userDao = new UserDao_v5();
        userDao.setConnectionMaker(connectionMaker());
        return userDao;
    }
    @Bean
    public AccountDao accountDao(){
        return new AccountDao(connectionMaker());
    }
    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

}
