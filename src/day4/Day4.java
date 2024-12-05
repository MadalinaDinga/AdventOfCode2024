package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day4 {

    public static final String WANTED = "XMAS";

    public static void main(String[] args) {
        List<List<Character>> matrix = new ArrayList<>();
        int count = 0;
        try (BufferedReader bR = new BufferedReader(new FileReader("src/day4/in.txt"))) {
            String line;
            while ((line = bR.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (char ch : line.toCharArray()) {
                    row.add(ch);
                }
                matrix.add(row);

                count = get_xmas_count(matrix);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(count);

        // print matrix
        for (List<Character> row : matrix) {
            for (Character ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    private static int get_xmas_count(List<List<Character>> matrix) {
        int count = 0;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j) == 'X') {
                    // search to the right
                    count += check_direction(matrix, i, j, 0, 1);
                    // search to the left
                    count += check_direction(matrix, i, j, 0, -1);
                    // search up
                    count += check_direction(matrix, i, j, 1, 0);
                    // search down
                    count += check_direction(matrix, i, j, -1, 0);
                    // search upper left diagonal
                    count += check_direction(matrix, i, j, -1, -1);
                    // search upper right diagonal
                    count += check_direction(matrix, i, j, -1, 1);
                    // search lower left diagonal
                    count += check_direction(matrix, i, j, 1, -1);
                    // search lower right diagonal
                    count += check_direction(matrix, i, j, 1, +1);
                }
            }
        }
        return count;
    }

    private static int check_direction(List<List<Character>> matrix, int i, int j, int delta_i, int delta_j) {
        String found = "X";
        while (WANTED.contains(found) && found.length() < 4 && i + delta_i < matrix.size() && i + delta_i >= 0 && j + delta_j >= 0 && j + delta_j < matrix.get(i + delta_i).size()) {
            found += matrix.get(i + delta_i).get(j + delta_j);
            j = j + delta_j;
            i = i + delta_i;
        }
        return WANTED.equals(found) ? 1 : 0;
    }
}

