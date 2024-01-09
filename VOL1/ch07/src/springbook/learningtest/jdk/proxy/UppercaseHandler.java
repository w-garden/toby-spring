package springbook.learningtest.jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
    Object target;


    UppercaseHandler(Object target) { //dynamic proxy로부터 전달받은 요청을 다시 target object에 위임하기 위해 target object를 주입받음
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ret = method.invoke(target, args); //타깃으로 위임. 인터페이스의 메서드 호출에 모두 적용됨
        if (ret instanceof String && method.getName().startsWith("say")) {
            return ((String) ret).toUpperCase();
        } else {
            return ret;
        }
    }
}
