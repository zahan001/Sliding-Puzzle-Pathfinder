import java.util.*;

public class SlidingPuzzle {
    static final int INF = Integer.MAX_VALUE; // Infinity value

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Dijkstra's algorithm to find the shortest path between two points on a grid
    static int dijkstra(char[][] grid, Point start, Point end) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Distance array to store shortest distance from start to each cell
        int[][] distance = new int[rows][cols];

        // Visited array to keep track of visited cells
        boolean[][] visited = new boolean[rows][cols];

        // Priority queue to prioritize cells with shortest distance
        PriorityQueue<Point> pq = new PriorityQueue<>((a, b) -> distance[a.x][a.y] - distance[b.x][b.y]);

        // Directions array to explore neighboring cells (up, down, left, right)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

        // Initialize distances to infinity
        for (int i = 0; i < rows; i++) {
            Arrays.fill(distance[i], INF);
        }

        // Distance from start to start is 0
        distance[start.x][start.y] = 0;
        pq.offer(start);

        // Dijkstra's algorithm main loop
        while (!pq.isEmpty()) {
            Point current = pq.poll();
            if (visited[current.x][current.y]) continue; // Skip if already visited
            visited[current.x][current.y] = true;

            // Print vertex information (for debugging)
            System.out.println("Vertex: (" + current.x + ", " + current.y + ") Distance from source: " + distance[current.x][current.y]);

            // If we reach the end point, return the distance
            if (current.x == end.x && current.y == end.y) {
                return distance[end.x][end.y];
            }

            // Explore neighbors
            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                // Check if the neighbor is within grid bounds and is not a wall
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY] != '0') {
                    // Calculate the weight of the edge (movement cost)
                    int weight = grid[newX][newY] == '.' ? 1 : 0; // Non-wall cells have weight 1

                    // Calculate the new distance from start to neighbor
                    if (distance[current.x][current.y] + weight < distance[newX][newY]) { // If the new distance is shorter than the current distance to the neighbor, // update the distance and add the neighbor to the priority queue
                        distance[newX][newY] = distance[current.x][current.y] + weight;
                        pq.offer(new Point(newX, newY));
                    }
                }
            }
        }

        return -1; // No path found
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'.', '.', '.', '.', '0', '.', '.', 'S'},
                {'.', '.', '.', '0', '.', '.', '.', '.'},
                {'0', '.', '.', '.', '.', '0', '.', '0'},
                {'.', '.', '.', '0', '.', '.', '.', '0'},
                {'F', '.', '.', '.', '.', '.', '0', '0'},
                {'.', '0', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '0'},
                {'.', '0', '.', '0', '.', '0', '.', '0'},
                {'0', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '0', '0', '.', '.', '.', '.', '0'}
        };

        //Point start = new Point(0, 7);
        //Point finish = new Point(4, 0);

        Point start = null;
        Point end = null;

        // Finding the start and end points in the grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'S') {
                    start = new Point(i, j);
                } else if (grid[i][j] == 'F') {
                    end = new Point(i, j);
                }
            }
        }

        // Check if start and end points are found
        if (start == null || end == null) {
            System.out.println("Start or end point not found.");
            return;
        }


        // Call Dijkstra's algorithm to find the shortest path length
        int shortestPathLength = dijkstra(grid, start, end);
        if (shortestPathLength != -1) {
            System.out.println("Shortest path length: " + shortestPathLength);
        } else {
            System.out.println("No path found.");
        }
    }
}
