package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day8 {
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

        int count = 0;
        Set<List<Integer>> antinodes = new HashSet<>();
        for (List<List<Integer>> frequencies : antennas.values()) {
            // Check each pair of antennas
            for (int i = 0; i < frequencies.size(); i++) {
                for (int j = i + 1; j < frequencies.size(); j++) {
                    List<List<Integer>> antinodesForPair = getAntinodesForPair(frequencies.get(i).get(0), frequencies.get(i).get(1), frequencies.get(j).get(0), frequencies.get(j).get(1));
                    count += !antinodes.contains(antinodesForPair.get(0)) && isWithinBounds(antinodesForPair.get(0).get(0), antinodesForPair.get(0).get(1), rowsCount, columnsCount) ? 1 : 0;
                    count += !antinodes.contains(antinodesForPair.get(1)) && isWithinBounds(antinodesForPair.get(1).get(0), antinodesForPair.get(1).get(1), rowsCount, columnsCount) ? 1 : 0;
                    antinodes.addAll(antinodesForPair);
                }
            }
        }
        System.out.println(count);
    }

    private static List<List<Integer>> getAntinodesForPair(Integer i1, Integer j1, Integer i2, Integer j2) {
        int iDif = i1 - i2;
        int jDif = j1 - j2;
        int iUp = i1 + iDif;
        int jUp = j1 + jDif;
        int iDown = i2 - iDif;
        int jDown = j2 - jDif;
        return List.of(List.of(iUp, jUp), List.of(iDown, jDown));
    }

    private static boolean isWithinBounds(int iUpperAntinode, int jUpperAntinode, int rowsCount, int columnsCount) {
        return iUpperAntinode >= 0 && iUpperAntinode < rowsCount && jUpperAntinode >= 0 && jUpperAntinode < columnsCount;
    }
}
