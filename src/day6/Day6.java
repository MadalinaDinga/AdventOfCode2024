package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Day6 {
    public static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static final char OBSTACLE = '#';
    public static final char VISITED = 'X';

    public static void main(String[] args) throws IOException {
        List<char[]> maze = readMaze("src/day6/in1.txt");

        int[] currentPosition = getStartingPosition(maze);
        if (currentPosition == null) {
            throw new IllegalArgumentException("No guard on the map");
        }

        System.out.println("Starting point: (" + currentPosition[1] + ", " + currentPosition[2] + ") direction: " + currentPosition[0]);

        int visitedCount = getVisitedCount(currentPosition, maze);
        System.out.println(visitedCount);

        printMaze(maze);

    }

    private static List<char[]> readMaze(String fileName) throws IOException {
        List<char[]> maze = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                maze.add(line.toCharArray());
            }
        }
        return maze;
    }

    private static void printMaze(List<char[]> maze) {
        for (char[] row : maze) {
            for (char col : row) {
                System.out.print(col);
            }
            System.out.println();
        }
    }

    private static int getVisitedCount(int[] currentPosition, List<char[]> maze) {
        int i = currentPosition[1], j = currentPosition[2];
        int directionIndex = currentPosition[0];
        int visitedCount = 1;
        maze.get(i)[j] = VISITED;
        while (true) {
            while (isWithinBounds(maze, i + DIRECTIONS[directionIndex][0], j + DIRECTIONS[directionIndex][1])
                    && maze.get(i + DIRECTIONS[directionIndex][0])[j + DIRECTIONS[directionIndex][1]] != OBSTACLE) {
                i += DIRECTIONS[directionIndex][0];
                j += DIRECTIONS[directionIndex][1];
                if (maze.get(i)[j] != VISITED) {
                    maze.get(i)[j] = VISITED;
                    visitedCount++;
                }
            }
            if (!isWithinBounds(maze, i + DIRECTIONS[directionIndex][0], j + DIRECTIONS[directionIndex][1])) {
                return visitedCount;
            }
            directionIndex = (directionIndex + 1) % 4;
        }
    }

    private static boolean isWithinBounds(List<char[]> maze, int i, int j) {
        return i >= 0 && i < maze.size() && j >= 0 && j < maze.size();
    }

    /**
     * Finds the current position of the guard.
     *
     * @param maze the maze to search
     * @return an array with elements representing the direction index and the coordinates of the guard, or null if no guard is found
     */
    private static int[] getStartingPosition(List<char[]> maze) {
        for (int i = 0; i < maze.size(); i++) {
            for (int j = 0; j < maze.get(i).length; j++) {
                char element = maze.get(i)[j];
                int dir = switch (element) {
                    case '^' -> 0;
                    case '>' -> 1;
                    case 'v' -> 2;
                    case '<' -> 3;
                    default -> -1;  // If not a direction, continue searching
                };
                if (dir != -1) {
                    // Return as soon as a valid direction is found
                    return new int[]{dir, i, j};
                }
            }
        }
        return null;
    }
}
