// IIT Student Number: 20220255
// Student Name: Sahan Madhawa Jayaweera
// UoW id: w2002471 | UoW number: 2002471

import java.io.*;;
import java.util.*;

public class SlidingPuzzle {
    static final int INF = Integer.MAX_VALUE; // Infinity value for distance initialization

    static class Point { // Class representing a point (cell) on the grid
        int x, y;

        Point(int x, int y) { // Constructor to initialize the coordinates of the point
            this.x = x;
            this.y = y;
        }
    }

    // Dijkstra's algorithm to find the shortest path between two points on a grid
    static List<String> dijkstra(char[][] grid, Point start, Point end) {
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
        String[] directionNames = {"up", "down", "left", "right"};

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
                List<String> pathSteps = new ArrayList<>();
                while (current != null) {
                    pathSteps.add("(" + current.x + "," + current.y + ")");
                    current = parent[current.x][current.y];
                }
                Collections.reverse(pathSteps);
                return pathSteps;
            }

            // Explore neighbors
            for (int i = 0; i < directions.length; i++) {
                int newX = current.x + directions[i][0];
                int newY = current.y + directions[i][1];

                // Check if the neighbor is within grid bounds and is not a wall
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY] != '0') {
                    // Calculate the weight of the edge (movement cost)
                    int weight = grid[newX][newY] == '.' ? 1 : 0; // Non-wall cells have weight 1

                    // Calculate the new distance from start to neighbor
                    if (distance[current.x][current.y] + weight < distance[newX][newY]) {
                        // If the new distance is shorter than the current distance to the neighbor,
                        // update the distance and add the neighbor to the priority queue
                        distance[newX][newY] = distance[current.x][current.y] + weight;
                        pq.offer(new Point(newX, newY));
                        parent[newX][newY] = current; // Update parent
                    }
                }
            }
        }

        return null; // No path found
    }


    // Main method to execute the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for the name of the file to be read
        System.out.println("Enter the name of the file to be read: ");
        String fileName = scanner.nextLine();
        scanner.close(); // Close the scanner

        // Specify the relative directory where the input files are located
        //String directory = "/Algo CW/src/";
        String filePath = "src/" + fileName;

        // Verify that the file exists
        File file = new File(filePath);
        // Verify that the file exists
        if (!file.exists()) {
            System.err.println("Error: Input file does not exist. File path: " + file.getAbsolutePath());
            return;
        }


        // Read the contents of the file and store them in a list
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Determine the dimensions of the grid
        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];

        // Initialize start and end points
        Point start = null;
        Point end = null;

        // Parsing the map and initializing the grid
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

        // Check if start and end points were found
        if (start == null || end == null) {
            System.out.println("Start or end point not found.");
            return;
        }

        // Perform Dijkstra's algorithm to find the shortest path
        List<String> shortestPath = dijkstra(grid, start, end);
        if (shortestPath != null) {
            // Output the length of the shortest path
            System.out.println("Shortest path length: " + shortestPath.size());
            // Output the steps of the solution
            System.out.println("Steps:");
            Point current = start;
            for (int i = 0; i < shortestPath.size(); i++) {
                String[] step = shortestPath.get(i).split(",");
                int x = Integer.parseInt(step[0].substring(1));
                int y = Integer.parseInt(step[1].substring(0, step[1].length() - 1));
                String direction = getDirection(current, new Point(x, y));if (i == 0) {
                    System.out.println("1. Start at (" + current.x + "," + current.y + ")");
                } else {
                    System.out.println((i + 1) + ". Move " + direction + " to (" + x + "," + y + ")");
                }
                current = new Point(x, y);
            }
            System.out.println((shortestPath.size() + 1) + ". Done!");
        } else {
            System.out.println("No path found."); // No path found print this message
        }

        //scanner.close(); // Close the scanner
    }

    // Helper method to determine the direction of movement
    private static String getDirection(Point current, Point next) {
        if (current.x < next.x) {
            return "down";
        } else if (current.x > next.x) {
            return "up";
        } else if (current.y < next.y) {
            return "right";
        } else {
            return "left";
        }
    }
}
