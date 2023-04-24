package me.erken.efe.chess.model;

public class Board {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private final Square[][] grid;
    private final King kingBlack = new King(Color.BLACK);
    private final King kingWhite = new King(Color.WHITE);

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
        grid[0][4].setPiece(kingBlack);
        grid[HEIGHT - 1][4].setPiece(kingWhite);
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

    public Coordinates findSquare(Square square) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (grid[y][x] == square) {
                    return new Coordinates(x, y);
                }
            }
        }
        return null;
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

    public King getKing(Color color) {
        return (color == Color.BLACK) ? kingBlack : kingWhite;
    }

    public void setKingStatus(Color color, boolean inCheckStatus) {
        if ((color == Color.BLACK)) {
            kingBlack.setInCheck(inCheckStatus);
        } else {
            kingWhite.setInCheck(inCheckStatus);
        }
    }

    private void highlightMoves(Piece piece) {
        for (Coordinates pos :
                piece.legalPositions) {
            getSquare(pos).setState(SquareState.HIGHLIGHTED);
        }
    }

    public void selectSquare(Square square) {
        if (square.getPiece() != null) {
            square.setState(SquareState.SELECTED);
            highlightMoves(square.getPiece());
        }
    }

    public void clearStateSquares() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j].setState(SquareState.NORMAL);
            }
        }
    }

    private void clearDangerSquares() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j].setDanger(SquareDanger.PEACEFUL);
            }
        }
    }

    private void setAttackedSquares(Coordinates[] attackPos, Color origin) {
        for (int i = 0; attackPos[i] != null; i++) {
            Square current = getSquare(attackPos[i]);
            if (current.getDanger() == SquareDanger.PEACEFUL) {
                current.setDanger((origin == Color.BLACK) ? SquareDanger.BLACK_ATTACKING : SquareDanger.WHITE_ATTACKING);
            } else {
                current.setDanger(SquareDanger.BOTH_ATTACKING);
            }
        }
    }

    public void updateDangerSquares() {
        clearDangerSquares();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Piece p = grid[i][j].getPiece();
                if (p != null) {
                    setAttackedSquares(p.attackingPositions.toArray(new Coordinates[0]), p.getColor());
                }
            }
        }
    }
}
