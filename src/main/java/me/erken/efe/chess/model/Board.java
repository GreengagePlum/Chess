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
        fillAllPieces();
    }

    private void fillPawn() {
        for (int x = 0; x < WIDTH; x++) {
            grid[1][x].setPiece(new Pawn(Color.BLACK));
        }
        for (int x = 0; x < WIDTH; x++) {
            grid[HEIGHT - 2][x].setPiece(new Pawn(Color.WHITE));
        }
    }

    private void fillRook() {
        grid[0][0].setPiece(new Rook(Color.BLACK));
        grid[0][WIDTH - 1].setPiece(new Rook(Color.BLACK));
        grid[HEIGHT - 1][0].setPiece(new Rook(Color.WHITE));
        grid[HEIGHT - 1][WIDTH - 1].setPiece(new Rook(Color.WHITE));
    }

    private void fillKnight() {
        grid[0][1].setPiece(new Knight(Color.BLACK));
        grid[0][WIDTH - 2].setPiece(new Knight(Color.BLACK));
        grid[HEIGHT - 1][1].setPiece(new Knight(Color.WHITE));
        grid[HEIGHT - 1][WIDTH - 2].setPiece(new Knight(Color.WHITE));
    }

    private void fillBishop() {
        grid[0][2].setPiece(new Bishop(Color.BLACK));
        grid[0][WIDTH - 3].setPiece(new Bishop(Color.BLACK));
        grid[HEIGHT - 1][2].setPiece(new Bishop(Color.WHITE));
        grid[HEIGHT - 1][WIDTH - 3].setPiece(new Bishop(Color.WHITE));
    }

    private void fillQueen() {
        grid[0][3].setPiece(new Queen(Color.BLACK));
        grid[HEIGHT - 1][3].setPiece(new Queen(Color.WHITE));
    }

    private void fillKing() {
        grid[0][4].setPiece(new King(Color.BLACK));
        grid[HEIGHT - 1][4].setPiece(new King(Color.WHITE));
    }

    private void fillAllPieces() {
        fillPawn();
        fillRook();
        fillBishop();
        fillKnight();
        fillQueen();
        fillKing();
    }

    public Square getSquare(Coordinates coords) {
        return grid[coords.y][coords.x];
    }

    public Coordinates findPiece(Piece piece) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (grid[y][x].getPiece() == piece) {
                    return new Coordinates(x, y);
                }
            }
        }
        return null;
    }

    private void highlightMoves(Piece piece) {
        Coordinates[] moves = piece.movablePositions(findPiece(piece), this);
        for (Coordinates coords : moves) {
            getSquare(coords).setState(SquareState.HIGHLIGHTED);
        }
    }

    public void selectSquare(Square square) {
        if (square.getPiece() != null) {
            square.setState(SquareState.SELECTED);
            highlightMoves(square.getPiece());
        }
    }

    public void clearSquares() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j].setState(SquareState.NORMAL);
            }
        }
    }

    public void movePiece(Square source, Square destination) {
        destination.setPiece(source.getPiece());
        source.setPiece(null);
    }
}
