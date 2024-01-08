package springbook.service;

import org.springframework.transaction.annotation.Transactional;
import springbook.domain.User;

import java.util.List;

@Transactional
public interface UserService {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    void update(User user);
    void upgradeLevels();
}
