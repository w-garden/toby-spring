package springbook.user.service;

import springbook.dao.UserDao;
import springbook.domain.User;

public class TestUserService extends UserService_v1 {
    @Override
    public void upgradeLevel(User user) {
        super.upgradeLevel(user);
    }
}
