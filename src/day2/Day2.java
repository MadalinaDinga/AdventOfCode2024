package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class Day2 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/day2/in.txt"))) {
            String line;
            int safe = 0;
            while ((line = br.readLine()) != null) {
                String[] report = line.split("\\s+");
                if (is_report_safe(report)) {
                    safe++;
                    continue;
                }
                // Eliminate each element in the list and test the remaining array
                for (int i = 0; i < report.length; i++) {
                    String[] before = Arrays.copyOfRange(report, i + 1, report.length);
                    String[] after = Arrays.copyOfRange(report, 0, i);

                    String[] listWithoutElem = new String[before.length + after.length];

                    System.arraycopy(after, 0, listWithoutElem, 0, after.length);
                    System.arraycopy(before, 0, listWithoutElem, after.length, before.length);

                    if (is_report_safe(listWithoutElem)) {
                        safe++;
                        break;
                    }
                }
            }
            System.out.println(safe);
        }
    }

    private static boolean is_report_safe(String[] report) {
        int first = Integer.parseInt(report[0]);
        int second = Integer.parseInt(report[1]);
        boolean is_asc = first < second;

        for (int i = 0; i < report.length - 1; i++) {
            // Check order
            int current = Integer.parseInt(report[i]);
            int next = Integer.parseInt(report[i + 1]);
            if (is_asc) {
                if (current >= next) {
                    return false;
                }
            } else {
                if (current <= next) {
                    return false;
                }
            }

            // Check difference
            int diff = Math.abs(next - current);
            if (diff < 1 || diff > 3) {
                return false;
            }
        }
        return true;
    }
}
