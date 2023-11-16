package springbook.user.dao;


import javax.sql.DataSource;

public class AccountDao_v6 {
    DataSource dataSource;
    public AccountDao_v6(DataSource dataSource) {
        this.dataSource=dataSource;
    }
}
