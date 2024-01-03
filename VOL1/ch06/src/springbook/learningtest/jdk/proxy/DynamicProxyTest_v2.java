package springbook.learningtest.jdk.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
/**
 ProxyFactoryBean 적용(addAdvice)
 */
public class DynamicProxyTest_v2 {

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget()); //타깃 설정
        pfBean.addAdvice(new UppercaseAdvice()); //부가기능을 담은 어드바이스. 여러 개 추가도 가능

        Hello proxiedHello = (Hello) pfBean.getObject(); //FactoryBean이므로 getObject로 생성된 프록시를 가져온다.
        assertThat(proxiedHello.sayHello("Hochul"), is("HELLO HOCHUL"));
        assertThat(proxiedHello.sayHi("Hochul"), is("HI HOCHUL"));
        assertThat(proxiedHello.sayThankYou("Hochul"), is("THANK YOU HOCHUL"));
    }

    static class UppercaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            String ret = (String) methodInvocation.proceed(); //메서드 실행시 메서드 정보와 타깃 오브젝트를 알고 있기 때문에 타깃 오브젝트를 전달할 필요가 없음
            return ret.toUpperCase();
        }
    }
}

