package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

class Day1 {
    public static void main(String[] args) throws IOException {
        Path inputPath = Path.of("src/day1/in.txt");
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        List<String> lines = Files.readAllLines(inputPath);
        for (String line : lines) {
            String[] columns = line.split("\\s+");

            list1.add(Integer.parseInt(columns[0]));
            list2.add(Integer.parseInt(columns[1]));
        }

        List<Integer> sortedList1 = list1.stream().sorted().toList();
        List<Integer> sortedList2 = list2.stream().sorted().toList();

        int dist = 0;
        for (int i = 0; i < sortedList1.size(); i++) {
            dist += Math.abs(sortedList1.get(i) - sortedList2.get(i));
        }

        int distAlt = IntStream.range(0, sortedList1.size())
                               .map(i -> Math.abs(sortedList1.get(i) - sortedList2.get(i)))
                               .sum();

        System.out.println(dist);
        System.out.println(distAlt);

        // Probl 2: Similarity score
        Map<Integer, Integer> counterMap = new HashMap<>();
        for (int dest : list2) {
            counterMap.merge(dest, 1, Integer::sum);
        }

        int sum = 0;
        for (int el : list1) {
            sum += el*counterMap.getOrDefault(el, 0);
        }
        System.out.println(sum);
    }
}