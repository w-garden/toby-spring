package springbook.user.v6.dao;


import javax.sql.DataSource;

public class AccountDao {
    DataSource dataSource;
    public AccountDao(DataSource dataSource) {
        this.dataSource=dataSource;
    }
}
