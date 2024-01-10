package springbook.user.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {
    PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            Object ret = methodInvocation.proceed(); //콜백을 호출해서 타깃의 메서드를 실행. 타깃 메서드 호출 전후로 필요한 부가기능 삽입 가능
            transactionManager.commit(status);
            return ret;
        }catch (RuntimeException e){
            this.transactionManager.rollback(status);
            throw e;
        }
    }
}
