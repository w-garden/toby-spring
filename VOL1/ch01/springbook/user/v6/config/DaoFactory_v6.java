package springbook.user.v6.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import springbook.user.v6.dao.AccountDao;
import springbook.user.v6.dao.UserDao_v6;

import javax.sql.DataSource;


@Configuration
public class DaoFactory_v6 {

    @Bean
    public UserDao_v6 userDao() {
        UserDao_v6 userDao = new UserDao_v6();
        userDao.setDataSource(dataSource());
        return userDao;
    }
    @Bean
    public AccountDao accountDao(){
        AccountDao accountDao = new AccountDao(dataSource());
        return accountDao;
    }

    @Bean
    public DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/spring");
        dataSource.setUsername("spring");
        dataSource.setPassword("spring");
        return dataSource;

    }
}
