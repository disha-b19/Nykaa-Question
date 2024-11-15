### Approach

1. **Input Validation and Initialization**:
   - Parse the grid dimensions \( n \times m \).
   - Create a priority queue to handle cells by their current minimum cost.
   - Define a cost matrix initialized to infinity for all cells except the starting point (0, 0), which is initialized to its cost.

2. **Movement Directions**:
   - The robot can move **right** or **down**. Represent these directions as coordinate changes: \((0, 1)\) and \((1, 0)\).

3. **Obstacle Handling**:
   - Treat any cell with value `-1` as an obstacle, skipping it during exploration.

4. **Dijkstra's Algorithm**:
   - Start from (0, 0) with its cost.
   - Use the priority queue to explore neighboring cells. Update their cost in the priority queue if a lower-cost path is found.
   - Stop when reaching the bottom-right corner or when all reachable cells have been processed.

5. **Output**:
   - If the bottom-right corner is reached, return the computed minimum cost. Otherwise, return `-1`.

---

### Java Implementation

Here is the Java solution:

```Java
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
```
### Explanation of Example

For the given input:

```python
grid = [
    [1, 3, 1, 5],
    [2, -1, 4, 2],
    [3, 2, 5, 1],
    [4, 3, 2, 1]
]
```

The robot’s path is:

- Start at \((0, 0)\) with cost \(1\),
- Move to \((1, 0)\), adding \(2\),
- Move to \((2, 1)\), adding \(2\),
- Move to \((3, 2)\), adding \(2\),
- Reach \((3, 3)\), adding \(1\).

**Total Cost**: \(1 + 2 + 2 + 2 + 1 = 12\).

---

### Complexity Analysis

1. **Time Complexity**: \(O(n \cdot m \cdot \log(n \cdot m))\), where \(n \cdot m\) is the number of grid cells, and logarithmic time is due to the priority queue.
2. **Space Complexity**: \(O(n \cdot m)\) for the cost matrix and priority queue.

---

### Testing

You can test edge cases like:
- Fully blocked grid (except start and end).
- Grid with the maximum size \(1000 \times 1000\).
- Grids with minimal paths.
