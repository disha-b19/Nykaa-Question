import java.util.PriorityQueue;

public class PathfindingOptimization {

    static class Cell implements Comparable<Cell> {
        int x, y, cost;

        Cell(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        @Override
        public int compareTo(Cell other) {
            return this.cost - other.cost;
        }
    }

    public static int minEnergyCost(int[][] grid) {
        int n = grid.length, m = grid[0].length;

        // If the start or end is blocked
        if (grid[0][0] == -1 || grid[n - 1][m - 1] == -1) {
            return -1;
        }

        // Directions for moving right and down
        int[][] directions = {{0, 1}, {1, 0}};

        // Priority queue to keep track of the cells based on minimum cost
        PriorityQueue<Cell> pq = new PriorityQueue<>();
        pq.offer(new Cell(0, 0, grid[0][0]));

        // Cost matrix initialized to infinity
        int[][] costs = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                costs[i][j] = Integer.MAX_VALUE;
            }
        }
        costs[0][0] = grid[0][0];

        while (!pq.isEmpty()) {
            Cell current = pq.poll();
            int x = current.x, y = current.y, currentCost = current.cost;

            // If we've reached the bottom-right corner
            if (x == n - 1 && y == m - 1) {
                return currentCost;
            }

            for (int[] dir : directions) {
                int nx = x + dir[0], ny = y + dir[1];

                if (nx >= 0 && ny >= 0 && nx < n && ny < m && grid[nx][ny] != -1) {
                    int newCost = currentCost + grid[nx][ny];

                    if (newCost < costs[nx][ny]) {
                        costs[nx][ny] = newCost;
                        pq.offer(new Cell(nx, ny, newCost));
                    }
                }
            }
        }

        // If the destination is unreachable
        return -1;
    }

    public static void main(String[] args) {
        int[][] grid = {
            {1, 3, 1, 5},
            {2, -1, 4, 2},
            {3, 2, 5, 1},
            {4, 3, 2, 1}
        };

        int result = minEnergyCost(grid);
        System.out.println("Minimum Energy Cost: " + result);
    }
}
