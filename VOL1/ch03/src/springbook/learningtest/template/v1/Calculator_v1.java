package springbook.learningtest.template.v1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator_v1 {
    public Integer calcSum(String path) throws IOException {
        BufferedReaderCallback sumCallBack = new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                Integer sum = 0;
                String line = null;
                while ((line = br.readLine()) != null) {
                    sum += Integer.valueOf(line);
                }
                return sum;
            }
        };
        return fileReadTemplate(path, sumCallBack);
    }


    public Integer calcMultiply(String filepath) throws IOException {
        BufferedReaderCallback multiplyCallback = new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                Integer multiply = 1;
                String line = null;
                while ((line = br.readLine()) != null) {
                    multiply *= Integer.valueOf(line);
                }
                return multiply;
            }
        };
        return fileReadTemplate(filepath, multiplyCallback);
    }

    public Integer fileReadTemplate(String path, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            int ret = callback.doSomethingWithReader(br);
            return ret;
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





