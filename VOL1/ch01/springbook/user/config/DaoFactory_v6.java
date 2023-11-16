package springbook.user.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import springbook.user.dao.AccountDao_v6;
import springbook.user.dao.UserDao_v6;

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
    public AccountDao_v6 accountDao(){
        AccountDao_v6 accountDao = new AccountDao_v6(dataSource());
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
