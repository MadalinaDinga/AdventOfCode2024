package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Day5 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader bR = new BufferedReader(new FileReader("src/day5/in1.txt"))) {
            String line = bR.readLine();
            Map<Integer, Set<Integer>> rules = new HashMap<>();

            while (!line.strip().isEmpty()) {
                String[] rule = line.split("\\|");
                int n1 = Integer.parseInt(rule[0]);
                int n2 = Integer.parseInt(rule[1]);
                rules.computeIfAbsent(n1, k -> new HashSet<>()).add(n2);

                line = bR.readLine();
            }

            int count = 0;
            while ((line = bR.readLine()) != null) {
                String[] updates = line.split(",");
                count += get_value_for_update(updates, rules);
            }
            System.out.println(count);
        }
    }

    private static int get_value_for_update(String[] updates, Map<Integer, Set<Integer>> rules) {
        boolean is_correct = true;
        for (int i = 0; i < updates.length; i++) {
            int updateVal = Integer.parseInt(updates[i]);

            for (int j = i - 1; j >= 0; j--) {
                Set<Integer> numbersForValue = rules.get(updateVal);
                int previousVal = Integer.parseInt(updates[j]);
                if (numbersForValue != null && numbersForValue.contains(previousVal)) {
                    is_correct = false;
                    break;
                }
            }
        }
        return is_correct ? Integer.parseInt(updates[updates.length / 2]) : 0;
    }
}
