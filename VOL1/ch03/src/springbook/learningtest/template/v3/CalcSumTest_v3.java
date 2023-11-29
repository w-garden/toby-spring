package springbook.learningtest.template.v3;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class CalcSumTest_v3 {
    Calculator_v3 calculator;
    String numFilepath;

    @Before
    public void setUp() {
        this.calculator = new Calculator_v3();
        this.numFilepath = getClass().getResource("../numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(this.numFilepath), is(10));
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(this.numFilepath), is(24));
    }
    @Test
    public void concatString() throws IOException{
        assertThat(calculator.concatenate(this.numFilepath), is("1234"));
    }
}
