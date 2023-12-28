package springbook.user.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.domain.User;

public class UserServiceTx implements UserService {
    UserService userService;
    PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void add(User user) {
        userService.add(user); //메서드 구현과 위임
    }

    @Override
    public void upgradeLevels() { //메서드 구현
        TransactionStatus status = this.transactionManager //부가 기능 수행
                .getTransaction(new DefaultTransactionDefinition());
        try {
            userService.upgradeLevels(); //위임
            this.transactionManager.commit(status); //부가 기능 수행
        } catch (RuntimeException e) {
            this.transactionManager.rollback(status);
            throw e;
        }
    }
}
