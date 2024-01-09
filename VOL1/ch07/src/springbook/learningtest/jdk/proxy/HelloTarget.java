package springbook.learningtest.jdk.proxy;
/**
 * 다이내믹 프록시를 이용한 프록시 만들기-Target Class
 */
public class HelloTarget implements Hello {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHi(String name) {
        return "Hi " +  name;
    }

    @Override
    public String sayThankYou(String name) {
        return "Thank You " + name;
    }
}
