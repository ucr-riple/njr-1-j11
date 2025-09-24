package other;

/**
 * Created by sobercheg on 12/4/13.
 */
public class ChessKing {

    private static int DESK_SIZE_X = 6;
    private static int DESK_SIZE_Y = 6;

    public static boolean move(int x, int y) {
        boolean[][] visited = new boolean[DESK_SIZE_X][DESK_SIZE_Y];
        int[][] fromX = new int[DESK_SIZE_X][DESK_SIZE_Y];
        int[][] fromY = new int[DESK_SIZE_X][DESK_SIZE_Y];
        visited[x][y] = true;
        boolean solutionFound = move(x, y, visited, fromX, fromY, 1);
        return solutionFound;
    }

    public static boolean move(int x, int y, boolean[][] visited, int[][] fromX, int[][] fromY, int level) {
        boolean solutionFound = tryMove(x, y, visited, fromX, fromY, level + 1, x - 2, y - 1)
                || tryMove(x, y, visited, fromX, fromY, level + 1, x - 2, y + 1)
                || tryMove(x, y, visited, fromX, fromY, level + 1, x - 1, y - 2)
                || tryMove(x, y, visited, fromX, fromY, level + 1, x - 1, y + 2)
                || tryMove(x, y, visited, fromX, fromY, level + 1, x + 1, y - 2)
                || tryMove(x, y, visited, fromX, fromY, level + 1, x + 1, y + 2)
                || tryMove(x, y, visited, fromX, fromY, level + 1, x + 2, y - 1)
                || tryMove(x, y, visited, fromX, fromY, level + 1, x + 2, y + 1);

        return solutionFound;
    }

    private static boolean tryMove(int x, int y, boolean[][] visited, int[][] fromX, int[][] fromY, int level, int newX, int newY) {
        if (newX < 0 || newY < 0 || newX >= DESK_SIZE_X || newY >= DESK_SIZE_Y) return false;
        if (visited[newX][newY]) return false;

        if (level == (DESK_SIZE_X * DESK_SIZE_Y)) {
            System.out.println("Congrats!! All cells are visited");
            for (int i = 0; i < (DESK_SIZE_X * DESK_SIZE_Y); i++) {
                System.out.print(x + " " + y + " -> ");
                int nX = fromX[x][y];
                int nY = fromY[x][y];
                x = nX;
                y = nY;
            }
            System.out.println();
            return true;
        }

        int oldFromX = fromX[newX][newY];
        int oldFromY = fromY[newX][newY];
        fromX[newX][newY] = x;
        fromY[newX][newY] = y;

        visited[newX][newY] = true;
        boolean found = move(newX, newY, visited, fromX, fromY, level);
        if (found) return true;
        visited[newX][newY] = false;

        fromX[newX][newY] = oldFromX;
        fromY[newX][newY] = oldFromY;
        return false;
    }

    public static void main(String[] args) {
        int solutionCounter = 0;
        for (int i = 0; i < DESK_SIZE_X; i++)
            for (int j = 0; j < DESK_SIZE_Y; j++)
                if (move(i, j)) {
                    System.out.println("Solution found for the starting point [" + i + ", " + j + "]");
                    solutionCounter++;
                }
        System.out.println("Total solutions found: " + solutionCounter);
    }

}
