package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day7step2 {
    private static final char[] OPERATORS = {'+', '*', '|'};

    public static void main(String[] args) throws IOException {
        try (BufferedReader bR = new BufferedReader(new FileReader("src/day7/in1.txt"))) {
            String line;
            long sum = 0;
            while ((line = bR.readLine()) != null) {
                String[] equation = line.split(":");
                long result = Long.parseLong(equation[0]);
                long[] numbers = Arrays.stream(equation[1].trim().split("\\s+")).mapToLong(Long::parseLong).toArray();

                sum += backtrack(0, numbers[0], numbers, result) ? result : 0;
            }
            System.out.println(sum);
        }
    }

    private static boolean backtrack(int i, long currentResult, long[] numbers, long result) {
        if (i == numbers.length - 1) {
            return result == currentResult;
        }
        for (char operator : OPERATORS) {
            long newResult = switch (operator) {
                case '+' -> currentResult + numbers[i + 1];
                case '*' -> currentResult * numbers[i + 1];
                case '|' -> Long.parseLong(currentResult + "" + numbers[i + 1]);
                default -> throw new IllegalStateException("Unexpected value: " + operator);
            };
            if (currentResult > result) {
                return false;
            }
            // Short-circuit the backtracking once a solution is found
            if (backtrack(i + 1, newResult, numbers, result)) {
                return true;
            }
        }
        return false;
    }
}
