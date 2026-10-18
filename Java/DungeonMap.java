
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class DungeonMap {

    public enum TileType {
        WALL, FLOOR, START, BOSS, TREASURE, MONSTER
    }

    private static final int[][] DIRECTIONS = {
        {0, -1}, {0, 1}, {-1, 0}, {1, 0}
    };

    private final int width;
    private final int height;
    private final TileType[][] grid;
    private final Random random;

    public DungeonMap(int width, int height) {
        this(width, height, new Random());
    }

    // Optional seed for reproducible dungeon generation.
    public DungeonMap(int width, int height, long seed) {
        this(width, height, new Random(seed));
    }

    private DungeonMap(int width, int height, Random random) {
        if (width < 3 || height < 3 ||
            (long) (width - 2) * (height - 2) < 2) {
            throw new IllegalArgumentException(
                "Map needs at least two interior tiles."
            );
        }

        this.width = width;
        this.height = height;
        this.grid = new TileType[height][width];
        this.random = random;
    }

    public void generateDungeon(double targetFloorPercent) {

        // Validate the requested percentage.
        if (!Double.isFinite(targetFloorPercent) ||
            targetFloorPercent <= 0 ||
            targetFloorPercent > 1) {
            throw new IllegalArgumentException(
                "Fill percentage must be between 0 and 1."
            );
        }

        long totalTiles = (long) width * height;
        long maximumFloorTiles =
            (long) (width - 2) * (height - 2);

        long targetFloorTiles =
            (long) (totalTiles * targetFloorPercent);

        if (targetFloorTiles < 2) {
            throw new IllegalArgumentException(
                "Not enough floor tiles for a start and boss."
            );
        }

        if (targetFloorTiles > maximumFloorTiles) {
            throw new IllegalArgumentException(
                "Requested " + targetFloorTiles +
                " floor tiles, but only " + maximumFloorTiles +
                " fit inside the border. Maximum coverage: " +
                String.format(
                    "%.2f%%",
                    100.0 * maximumFloorTiles / totalTiles
                )
            );
        }

        // Fill the entire map with walls.
        for (TileType[] row : grid) {
            Arrays.fill(row, TileType.WALL);
        }

        int currentX = width / 2;
        int currentY = height / 2;
        long floorCount = 1;

        List<int[]> floorPositions = new ArrayList<>();

        grid[currentY][currentX] = TileType.FLOOR;
        floorPositions.add(new int[]{currentX, currentY});

        // Random Walk dungeon generation.
        while (floorCount < targetFloorTiles) {

            int[] direction =
                DIRECTIONS[random.nextInt(DIRECTIONS.length)];

            int nextX = currentX + direction[0];
            int nextY = currentY + direction[1];

            if (nextX > 0 && nextX < width - 1 &&
                nextY > 0 && nextY < height - 1) {

                currentX = nextX;
                currentY = nextY;

                if (grid[currentY][currentX] == TileType.WALL) {
                    grid[currentY][currentX] = TileType.FLOOR;
                    floorPositions.add(
                        new int[]{currentX, currentY}
                    );
                    floorCount++;
                }

            } else {
                // Restart from an existing floor tile.
                int[] randomFloor = floorPositions.get(
                    random.nextInt(floorPositions.size())
                );

                currentX = randomFloor[0];
                currentY = randomFloor[1];
            }
        }

        assignRoomTypes(floorPositions);
    }

    /**
     * Breadth-First Search (BFS).
     *
     * Finds the floor tile with the greatest
     * shortest-path distance from the starting tile.
     */
    private int[] findFarthestFloor(int startX, int startY) {

        int[][] distance = new int[height][width];

        for (int[] row : distance) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> queue = new ArrayDeque<>();

        queue.add(new int[]{startX, startY});
        distance[startY][startX] = 0;

        int[] farthest = {startX, startY};
        int maxDistance = 0;

        while (!queue.isEmpty()) {

            int[] current = queue.remove();

            int x = current[0];
            int y = current[1];

            for (int[] direction : DIRECTIONS) {

                int nextX = x + direction[0];
                int nextY = y + direction[1];

                // Check map boundaries.
                if (nextX < 0 || nextX >= width ||
                    nextY < 0 || nextY >= height) {
                    continue;
                }

                // Only visit unvisited floor tiles.
                if (grid[nextY][nextX] != TileType.FLOOR ||
                    distance[nextY][nextX] != -1) {
                    continue;
                }

                distance[nextY][nextX] =
                    distance[y][x] + 1;

                queue.add(new int[]{nextX, nextY});

                // Update the farthest reachable tile.
                if (distance[nextY][nextX] > maxDistance) {
                    maxDistance = distance[nextY][nextX];

                    farthest = new int[]{
                        nextX, nextY
                    };
                }
            }
        }

        return farthest;
    }

    private void assignRoomTypes(List<int[]> floorPositions) {

        // The first carved tile is the starting tile.
        int[] start = floorPositions.get(0);

        // BFS determines the boss location.
        int[] boss = findFarthestFloor(
            start[0], start[1]
        );

        grid[start[1]][start[0]] = TileType.START;
        grid[boss[1]][boss[0]] = TileType.BOSS;

        // Exclude start and boss from random encounters.
        List<int[]> pool = new ArrayList<>();

        for (int[] floor : floorPositions) {

            boolean isStart =
                floor[0] == start[0] &&
                floor[1] == start[1];

            boolean isBoss =
                floor[0] == boss[0] &&
                floor[1] == boss[1];

            if (!isStart && !isBoss) {
                pool.add(floor);
            }
        }

        Collections.shuffle(pool, random);

        int treasureCount = (int) (pool.size() * 0.05);
        int monsterCount = (int) (pool.size() * 0.20);

        int index = 0;

        // Place treasure.
        for (int i = 0; i < treasureCount; i++) {

            int[] tile = pool.get(index++);

            grid[tile[1]][tile[0]] = TileType.TREASURE;
        }

        // Place monsters.
        for (int i = 0; i < monsterCount; i++) {

            int[] tile = pool.get(index++);

            grid[tile[1]][tile[0]] = TileType.MONSTER;
        }
    }

    public void printDungeon() {

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                switch (grid[y][x]) {

                    case WALL ->
                        System.out.print("██");

                    case FLOOR ->
                        System.out.print("  ");

                    case START ->
                        System.out.print("S ");

                    case BOSS ->
                        System.out.print("B ");

                    case TREASURE ->
                        System.out.print("T ");

                    case MONSTER ->
                        System.out.print("M ");
                }
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {

        int width = 40;
        int height = 20;
        double fillPercentage = 0.35;

        DungeonMap dungeon =
            new DungeonMap(width, height);

        dungeon.generateDungeon(fillPercentage);
        dungeon.printDungeon();
    }
}
