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

    private static Cell slide(char[][] board, Cell start, int dx, int dy) {
        int newRow = start.row + dx;
        int newCol = start.col + dy;

        while (isValidMove(board, newRow, newCol) && board[newRow][newCol] != '0') {
            start.row = newRow;
            start.col = newCol;
            newRow += dx;
            newCol += dy;
        }
        return start;
    }

    public static List<Cell> findShortestPath(char[][] board, Cell start, Cell finish) {
        Map<Cell, Cell> parentMap = new HashMap<>();
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);

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

            for (int[] direction : DIRECTIONS){
                Cell newPosition = slide(board,  new Cell(current.row, current.col), direction[0], direction[1] );
                if(!visited.contains(newPosition)){
                    queue.offer(newPosition);
                    visited.add(newPosition);
                    parentMap.put(newPosition, current);

                }

                }
            }

            /*for (int[] direction : DIRECTIONS) {
                int[] newPosition = slide(board, current.row, current.col, direction[0], direction[1]);
                int newRow = newPosition[0];
                int newCol = newPosition[1];
                Cell neighbor = new Cell(newRow, newCol);
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    System.out.println("Adding neighbor: (" + newRow + ", " + newCol + ")");
                }
            }
        }*/

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
