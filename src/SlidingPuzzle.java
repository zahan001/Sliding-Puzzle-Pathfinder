import java.io.*;;
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
    // Dijkstra's algorithm to find the shortest path between two points on a grid
    static List<Point> dijkstra(char[][] grid, Point start, Point end) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Distance array to store shortest distance from start to each cell
        int[][] distance = new int[rows][cols];

        // Visited array to keep track of visited cells
        boolean[][] visited = new boolean[rows][cols];

        // Parent array to keep track of the path
        Point[][] parent = new Point[rows][cols];

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

            // If we reach the end point, reconstruct the path and return
            if (current.x == end.x && current.y == end.y) {
                List<Point> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parent[current.x][current.y];
                }
                Collections.reverse(path);
                return path;
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
                        parent[newX][newY] = current; // Update parent
                    }
                }
            }
        }

        return null; // No path found
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the file to be read: ");
        String fileName = scanner.nextLine();

        // Specify the directory where the input files are located
        String directory = "D:/IIT/2nd Year/Data Structures and Algo/Algo CW/src/";
        String filePath = directory + fileName;

        // Verify that the file exists
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Error: Input file does not exist.");
            return;
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            return;
        }

        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];

        Point start = null;
        Point end = null;

        // Parsing the map
        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                char ch = line.charAt(j);
                grid[i][j] = ch;
                if (ch == 'S') {
                    start = new Point(i, j);
                } else if (ch == 'F') {
                    end = new Point(i, j);
                }
            }
        }

        if (start == null || end == null) {
            System.out.println("Start or end point not found.");
            return;
        }

        List<Point> shortestPath = dijkstra(grid, start, end);
        if (shortestPath != null) {
            System.out.println("Shortest path length: " + shortestPath.size());
            System.out.println("Steps:");
            for (int i = 0; i < shortestPath.size(); i++) {
                Point step = shortestPath.get(i);
                System.out.println((i + 1) + ". Move to (" + step.x + ", " + step.y + ")");
            }
        } else {
            System.out.println("No path found.");
        }

        scanner.close();
    }
}
