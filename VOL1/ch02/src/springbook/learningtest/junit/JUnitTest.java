package springbook.learningtest.junit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 * JUnit 으로 만드는 JUnit 자신에 대한 테스트
 */
public class JUnitTest {
    static JUnitTest testObject;
    @Before
    public void print(){
        System.out.println(testObject);
    }
    @Test
    public void test1() {
        Assert.assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

    @Test
    public void test2() {
        Assert.assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

    @Test
    public void test3() {
        Assert.assertThat(this, is(not(sameInstance(testObject))));
        testObject=this;
    }
}
