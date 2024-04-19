import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.*;

public class SlidingPuzzle {
    static class Cell {
        int row;
        int col;

        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    // Define directions: up, down, left, right
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private static boolean isValidMove(char[][] board, int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

    // The slide method simulates the sliding behaviour of the player.
    // It starts from the current cell and continues sliding in the specified direction until it encounters a wall or a rock ('0').
    private static Cell slide(char[][] board, Cell start, int dx, int dy) {
        int newRow = start.row + dx;
        int newCol = start.col + dy;

        while (isValidMove(board, newRow, newCol) && board[newRow][newCol] != '0') {
            newRow += dx;
            newCol += dy;
            if (!isValidMove(board, newRow, newCol)) {
                break;  // Stop sliding if the new position is invalid
            }
            System.out.println("Sliding to cell: (" + newRow + ", " + newCol + ")");
        }

        // If the new position is invalid or the cell is a wall ('0'), return the last valid position
        return new Cell(newRow - dx, newCol - dy);
    }





    // The findShortestPath method uses the slide method to explore neighboring cells while considering the sliding mechanics.
    // It slides the player in each direction and adds the resulting cell to the queue if it hasn't been visited before.
    public static List<Cell> findShortestPath(char[][] board, Cell start, Cell finish) {
        Map<Cell, Cell> parentMap = new HashMap<>();
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);
        System.out.println("Marked as visited: (" + start.row + ", " + start.col + ")");

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            System.out.println("Exploring cell: (" + current.row + ", " + current.col + ")");

            if (current.row == finish.row && current.col == finish.col) {
                // Reconstruct path
                System.out.println("Path found!");

                List<Cell> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parentMap.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            for (int[] direction : DIRECTIONS) {
                Cell newPosition = slide(board, current, direction[0], direction[1]);
                if (!visited.contains(newPosition)) {
                    queue.offer(newPosition);
                    visited.add(newPosition);
                    parentMap.put(newPosition, current);
                    System.out.println("Marked as visited: (" + newPosition.row + ", " + newPosition.col + ")");
                }
            }
        }

        System.out.println("No path found.");

        return Collections.emptyList();  // No path found
    }


    public static void main(String[] args) {

        // Example board
        char[][] board = {
                ".....0...S".toCharArray(),
                "....0.....".toCharArray(),
                "0.....0..0".toCharArray(),
                "...0....0.".toCharArray(),
                ".F......0.".toCharArray(),
                ".0........".toCharArray(),
                ".......0..".toCharArray(),
                ".0.0..0..0".toCharArray(),
                "0.........".toCharArray(),
                ".00.....0.".toCharArray()
        };

        Cell start = null;
        Cell finish = null;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'S') {
                    start = new Cell(i, j);
                } else if (board[i][j] == 'F') {
                    finish = new Cell(i, j);
                }
            }
        }

        List<Cell> path = findShortestPath(board, start, finish);
        if (!path.isEmpty()) {
            System.out.println("Shortest path found:");
            for (Cell cell : path) {
                System.out.println("(" + cell.row + ", " + cell.col + ")");
            }
        } else {
            System.out.println("No path found.");
        }
    }
}
