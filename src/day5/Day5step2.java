package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day5step2 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader bR = new BufferedReader(new FileReader("src/day5/in1.txt"))) {
            String line = bR.readLine();
            Map<String, Set<String>> rules = new HashMap<>();

            while (!line.strip().isEmpty()) {
                String[] rule = line.split("\\|");
                rules.computeIfAbsent(rule[0], k -> new HashSet<>()).add(rule[1]);

                line = bR.readLine();
            }

            int count = 0;
            while ((line = bR.readLine()) != null) {
                String[] updates = line.split(",");
                int[] incorrect_pair;
                boolean is_incorrect_pair = false;
                while ((incorrect_pair = get_next_violation(updates, rules)) != null) {
                    String temp = updates[incorrect_pair[0]];
                    updates[incorrect_pair[0]] = updates[incorrect_pair[1]];
                    updates[incorrect_pair[1]] = temp;
                    is_incorrect_pair = true;
                }
                if (is_incorrect_pair) {
                    count += Integer.parseInt(updates[updates.length / 2]);
                }
            }
            System.out.println(count);
        }
    }

    private static int[] get_next_violation(String[] updates, Map<String, Set<String>> rules) {
        for (int i = 0; i < updates.length; i++) {
            String updateVal = updates[i];

            for (int j = i - 1; j >= 0; j--) {
                Set<String> numbersForValue = rules.get(updateVal);
                if (numbersForValue != null && numbersForValue.contains(updates[j])) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
