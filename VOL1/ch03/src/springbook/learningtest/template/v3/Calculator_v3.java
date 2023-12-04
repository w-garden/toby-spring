package springbook.learningtest.template.v3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator_v3 {
    public Integer calcSum(String filepath) throws IOException {
        LineCallback_Generic<Integer> sumCallBack = (line, value) -> value + Integer.valueOf(line);
        return lineReadTemplate(filepath, sumCallBack, 0);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        LineCallback_Generic<Integer> lineCallback = (line, value) -> value * Integer.valueOf(line);
        return lineReadTemplate(filepath, lineCallback, 1);
    }

    public String concatenate(String filepath) throws IOException {
        LineCallback_Generic<String> concatenateCallback = (line, value) -> value + line;
        return lineReadTemplate(filepath, concatenateCallback, "");
    }


    public <T> T lineReadTemplate(String filepath, LineCallback_Generic<T> callback, T intVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            T res = intVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}





