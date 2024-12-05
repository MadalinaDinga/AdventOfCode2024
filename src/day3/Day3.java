package day3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/day3/in.txt"))) {
            String line;
            int sum = 0;
            while ((line = br.readLine()) != null) {
                sum += get_line_sum(line);
            }
            System.out.println(sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int get_line_sum(String line) {
        String regex = "mul\\((\\d+),(\\d+)\\)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);

        int sum = 0;
        while (m.find()) {
            String xStr = m.group(1);
            String yStr = m.group(2);
            sum += Integer.parseInt(xStr) * Integer.parseInt(yStr);
        }
        return sum;
    }
}
