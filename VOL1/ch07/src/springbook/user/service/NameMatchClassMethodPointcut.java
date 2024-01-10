package springbook.user.service;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut {
    public void setMappedClassName(String mappedClassName) {
        this.setClassFilter(new SimpleClassFilter(mappedClassName)); //프로퍼티로 받은 클래스 이름을 이용해서 필터를 만든다.

    }

    private class SimpleClassFilter implements ClassFilter {
        String mappedName;

        public SimpleClassFilter(String mappedName) {
            this.mappedName = mappedName;
        }

        @Override
        public boolean matches(Class<?> clazz) {
            return PatternMatchUtils.simpleMatch(mappedName, clazz.getSimpleName()); //simpleMatch : 와일드카드가 들어간 문자열 비교를 지원하는 스프링의 유틸리티 메서드
        }
    }
}
