package springbook.dao;

import springbook.domain.User;

import java.util.List;

public interface UserDao {
    public void add(User user);
    public void update(User user1);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();


}
