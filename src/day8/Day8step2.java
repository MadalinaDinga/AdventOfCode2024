package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day8step2 {
    public static void main(String[] args) throws IOException {
        Map<Character, List<List<Integer>>> antennas = new HashMap<>();
        int rowsCount = 0;
        int columnsCount = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/day8/in1.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                char[] lineArr = line.toCharArray();
                for (int j = 0; j < lineArr.length; j++) {
                    if (lineArr[j] == '.') {
                        continue;
                    }
                    antennas.computeIfAbsent(lineArr[j], k -> new ArrayList<>()).add(List.of(rowsCount, j));
                }
                rowsCount++;
                columnsCount = lineArr.length;
            }
        }
        System.out.println(antennas);
        System.out.println("Rows: " + rowsCount + " columns: " + columnsCount);

        Set<List<Integer>> antinodes = new HashSet<>();
        for (List<List<Integer>> frequencies : antennas.values()) {
            // Check each pair of antennass
            for (int i = 0; i < frequencies.size(); i++) {
                for (int j = i + 1; j < frequencies.size(); j++) {
                    Set<List<Integer>> antinodesForPair = getAntinodesForPair(frequencies.get(i).get(0), frequencies.get(i).get(1), frequencies.get(j).get(0), frequencies.get(j).get(1), rowsCount, columnsCount);
                    antinodes.addAll(antinodesForPair);
                }
            }
        }
        System.out.println(antinodes.size());
    }

    private static Set<List<Integer>> getAntinodesForPair(Integer i1, Integer j1, Integer i2, Integer j2, int rowsCount, int columnsCount) {
        Set<List<Integer>> antinodes = new HashSet<>();

        int iDif = i1 - i2;
        int jDif = j1 - j2;
        int k = 0;
        int iUp;
        int jUp;
        while (isWithinBounds((iUp = i1 + k * iDif), (jUp = j1 + k * jDif), rowsCount, columnsCount)) {
            antinodes.add(List.of(iUp, jUp));
            k++;
        }

        k = 0;
        int iDown;
        int jDown;
        while (isWithinBounds((iDown = i2 - k * iDif), (jDown = j2 - k * jDif), rowsCount, columnsCount)) {
            antinodes.add(List.of(iDown, jDown));
            k++;
        }
        return antinodes;
    }

    private static boolean isWithinBounds(int iUpperAntinode, int jUpperAntinode, int rowsCount, int columnsCount) {
        return iUpperAntinode >= 0 && iUpperAntinode < rowsCount && jUpperAntinode >= 0 && jUpperAntinode < columnsCount;
    }
}
