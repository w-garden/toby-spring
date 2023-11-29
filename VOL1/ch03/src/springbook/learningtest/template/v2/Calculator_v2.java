package springbook.learningtest.template.v2;

import springbook.learningtest.template.LineCallback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator_v2 {
    public Integer calcSum(String path) throws IOException {
        LineCallback sumCallBack = new LineCallback() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        };
        return lineReadTemplate(path, sumCallBack, 0);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        LineCallback lineCallback = new LineCallback() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.valueOf(line);
            }
        };

        return lineReadTemplate(filepath, lineCallback, 1);
    }

    public Integer lineReadTemplate(String filepath, LineCallback callback, int intVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            Integer res = intVal;
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





