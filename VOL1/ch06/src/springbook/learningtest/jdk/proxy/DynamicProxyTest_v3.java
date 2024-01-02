package springbook.learningtest.jdk.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DynamicProxyTest_v3 {

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        pfBean.addAdvice(new UppercaseAdvice()); //부가기능을 담은 어드바이스

        Hello proxiedHello = (Hello) pfBean.getObject();
        assertThat(proxiedHello.sayHello("Hochul"), is("HELLO HOCHUL"));
        assertThat(proxiedHello.sayHi("Hochul"), is("HI HOCHUL"));
        assertThat(proxiedHello.sayThankYou("Hochul"), is("THANK YOU HOCHUL"));
    }

    @Test
    public void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut(); //메서드 이름을 비교해서 대상을 선정하는 알고리즘을 제공하는 포인트 컷 생성
        pointcut.setMappedName("sayH*");

        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice())); //포인트 컷과 어드바이스를 Advisor로 묶어서 한번에 추가

        Hello proxiedHello = (Hello) pfBean.getObject();
        assertThat(proxiedHello.sayHello("Hochul"), is("HELLO HOCHUL"));
        assertThat(proxiedHello.sayHi("Hochul"), is("HI HOCHUL"));
        assertThat(proxiedHello.sayThankYou("Hochul"), is("Thank You Hochul"));
    }

    static class UppercaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            String ret = (String) methodInvocation.proceed(); //메서드 실행시 메서드 정보와 타깃 오브젝트를 알고 있기 때문에 타깃 오브젝트를 전달할 필요가 없음
            return ret.toUpperCase();
        }
    }
}

