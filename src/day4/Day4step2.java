package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day4step2 {

    public static void main(String[] args) throws IOException {
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
        }
        System.out.println(count);

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
                if (matrix.get(i).get(j) == 'A') {
                    boolean is_right_diagonal = (check_direction(matrix, i, j, -1, -1, 'M') && check_direction(matrix, i, j, 1, 1, 'S')) || (check_direction(matrix, i, j, -1, -1, 'S') && check_direction(matrix, i, j, 1, 1, 'M'));
                    boolean is_left_diagonal = (check_direction(matrix, i, j, 1, -1, 'M') && check_direction(matrix, i, j, -1, 1, 'S')) || (check_direction(matrix, i, j, 1, -1, 'S') && check_direction(matrix, i, j, -1, 1, 'M'));

                    if (is_right_diagonal && is_left_diagonal) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static boolean check_direction(List<List<Character>> matrix, int i, int j, int delta_i, int delta_j, char wanted) {
        if (i + delta_i >= matrix.size() || i + delta_i < 0 || j + delta_j < 0 || j + delta_j >= matrix.get(i).size()) {
            return false;
        }
        return matrix.get(i + delta_i).get(j + delta_j) == wanted;
    }
}

