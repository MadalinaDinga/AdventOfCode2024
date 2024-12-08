package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6step2 {
    public static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static final char OBSTACLE = '#';

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        List<char[]> maze = readMaze("src/day6/in1.txt");

        int[] currentPosition = getStartingPosition(maze);
        if (currentPosition == null) {
            throw new IllegalArgumentException("No guard on the map");
        }

        System.out.println("Starting point: (" + currentPosition[1] + ", " + currentPosition[2] + ") direction: " + currentPosition[0]);

        Set<List<Integer>> visitedRoute = getVisitedRoute(currentPosition, maze);
        int count = 0;
        for (List<Integer> position : visitedRoute) {
            List<char[]> obstructedMaze = placeObstacle(maze, position.get(0), position.get(1));
            if (hasLoop(currentPosition, obstructedMaze)) {
                count++;
            }
        }
        System.out.println(count);

        long end = System.currentTimeMillis();
        System.out.println("Execution took " + (end - start) + " milliseconds");
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

    private static boolean hasLoop(int[] currentPosition, List<char[]> maze) {
        int i = currentPosition[1], j = currentPosition[2];
        int directionIndex = currentPosition[0];
        Set<List<Integer>> visitedRouteWithDirections = new HashSet<>();
        while (true) {
            while (isWithinBounds(maze, i + DIRECTIONS[directionIndex][0], j + DIRECTIONS[directionIndex][1])
                    && maze.get(i + DIRECTIONS[directionIndex][0])[j + DIRECTIONS[directionIndex][1]] != OBSTACLE) {
                i += DIRECTIONS[directionIndex][0];
                j += DIRECTIONS[directionIndex][1];
                if (visitedRouteWithDirections.contains(List.of(directionIndex, i, j))) {
                    return true;
                }
                visitedRouteWithDirections.add(List.of(directionIndex, i, j));
            }
            if (!isWithinBounds(maze, i + DIRECTIONS[directionIndex][0], j + DIRECTIONS[directionIndex][1])) {
                return false;
            }
            directionIndex = (directionIndex + 1) % 4;
        }
    }

    private static List<char[]> placeObstacle(List<char[]> maze, int i, int j) {
        // Create a deep copy of the maze
        List<char[]> obstructedMaze = maze.stream().map(char[]::clone).toList();
        obstructedMaze.get(i)[j] = OBSTACLE;
        return obstructedMaze;
    }

    private static Set<List<Integer>> getVisitedRoute(int[] currentPosition, List<char[]> maze) {
        int i = currentPosition[1], j = currentPosition[2];
        int directionIndex = currentPosition[0];
        Set<List<Integer>> visitedRoute = new HashSet<>();
        while (true) {
            while (isWithinBounds(maze, i + DIRECTIONS[directionIndex][0], j + DIRECTIONS[directionIndex][1])
                    && maze.get(i + DIRECTIONS[directionIndex][0])[j + DIRECTIONS[directionIndex][1]] != OBSTACLE) {
                i += DIRECTIONS[directionIndex][0];
                j += DIRECTIONS[directionIndex][1];
                visitedRoute.add(List.of(i, j));
            }
            if (!isWithinBounds(maze, i + DIRECTIONS[directionIndex][0], j + DIRECTIONS[directionIndex][1])) {
                return visitedRoute;
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
