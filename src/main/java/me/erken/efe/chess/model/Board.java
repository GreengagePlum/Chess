package me.erken.efe.chess.model;

public class Board {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private final Square[][] grid;

    public Board() {
        grid = new Square[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = new Square();
            }
        }
        fillPawn();
    }

    private void fillPawn() {
        for (int x = 0; x < WIDTH; x++) {
            grid[1][x].setPiece(new Pawn(Color.BLACK, x, 1));
        }
        for (int x = 0; x < WIDTH; x++) {
            grid[HEIGHT - 2][x].setPiece(new Pawn(Color.WHITE, x, HEIGHT - 2));
        }
    }

    public Square getSquare(int positionX, int positionY) {
        return grid[positionY][positionX];
    }
}
