package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3step2 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/day3/in.txt"))) {
            String line;
            int sum = 0;
            boolean include = true;
            while ((line = br.readLine()) != null) {
                int currentIndex = 0;
                while (currentIndex < line.length()) {
                    int dontIndex = line.indexOf("don't()", currentIndex);
                    int doIndex = line.indexOf("do()", currentIndex);

                    int nextBoundary = Math.min(
                            (dontIndex == -1 ? Integer.MAX_VALUE : dontIndex),
                            (doIndex == -1 ? Integer.MAX_VALUE : doIndex)
                    );

                    if (nextBoundary == Integer.MAX_VALUE) {
                        String fragment = line.substring(currentIndex);
                        System.out.println(fragment);
                        sum += get_fragment_sum(fragment, include);
                        break;
                    }

                    String fragment = line.substring(currentIndex, nextBoundary);
                    System.out.println(fragment);
                    sum += get_fragment_sum(fragment, include);
                    if (nextBoundary == dontIndex) {
                        currentIndex = dontIndex + "don't()".length();
                        include = false;
                    } else {
                        currentIndex = doIndex + "do()".length();
                        include = true;
                    }
                }
            }
            System.out.println(sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int get_fragment_sum(String fragment, boolean include) {
        if (!include) {
            return 0;
        }
        String regex = "mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fragment);

        int sum = 0;
        while (matcher.find()) {
            String x = matcher.group(1);
            String y = matcher.group(2);
            sum += Integer.parseInt(x) * Integer.parseInt(y);
        }
        return sum;
    }
}
