package springbook.dao;

import springbook.domain.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    void update(User user1);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();


}
