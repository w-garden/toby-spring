package sample;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * 다이내믹 프록시를 이용한 프록시 만들기-Client
 */
public class HelloTest {
    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget(); //target은 인터페이스를 통해서 접근하다
        assertThat(hello.sayHello("Toby"), is("Hello Toby"));
        assertThat(hello.sayHi("Toby"), is("Hi Toby"));
        assertThat(hello.sayThankYou("Toby"), is("Thank You Toby"));

        Hello proxyHello = new HelloUppercase(new HelloTarget()); //proxy를 통해 target에 접근
        assertThat(proxyHello.sayHello("Hochul"), is("HELLO HOCHUL"));
        assertThat(proxyHello.sayHi("Hochul"), is("HI HOCHUL"));
        assertThat(proxyHello.sayThankYou("Hochul"), is("THANK YOU HOCHUL"));


        Hello dynamicProxyHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(), //동적으로 생성되는 다이내믹 프록시 클래스의 로딩에 사용할 클래스 로더
                new Class[]{Hello.class}, //구현할 인터페이스
                new UppercaseHandler(new HelloTarget()) //부가기능과 위임코드를 담은 InvocationHandler
        );
        assertThat(dynamicProxyHello.sayHello("Hochul"), is("HELLO HOCHUL"));
        assertThat(dynamicProxyHello.sayHi("Hochul"), is("HI HOCHUL"));
        assertThat(dynamicProxyHello.sayThankYou("Hochul"), is("THANK YOU HOCHUL"));

    }
}
