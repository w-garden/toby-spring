package springbook.user.service;

import springbook.domain.User;

public interface UserService {
    void add(User user);
    void upgradeLevels();
}
