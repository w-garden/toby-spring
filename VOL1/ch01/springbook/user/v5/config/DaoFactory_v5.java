package springbook.user.v5.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.user.v5.dao.AccountDao_v5;
import springbook.user.connection.ConnectionMaker;
import springbook.user.v5.dao.UserDao_v5;
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
    public AccountDao_v5 accountDao(){
        return new AccountDao_v5(connectionMaker());
    }
    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

}
