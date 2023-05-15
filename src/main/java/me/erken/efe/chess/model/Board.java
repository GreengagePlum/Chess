package me.erken.efe.chess.model;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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

    public <T extends Piece> List<Piece> getPieces(Class<T> pieceType) {
        List<Piece> result = new LinkedList<>();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Piece p = grid[y][x].getPiece();
                if (pieceType.isInstance(p)) {
                    result.add(p);
                }
            }
        }
        return result;
    }

    public <T extends Piece> List<Piece> getPieces(Class<T> pieceType, Color color) {
        List<Piece> result = new LinkedList<>();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Piece p = grid[y][x].getPiece();
                if (pieceType.isInstance(p) && p.getColor() == color) {
                    result.add(p);
                }
            }
        }
        return result;
    }

    public King getKing(Color color) {
        return (color == Color.BLACK) ? kingBlack : kingWhite;
    }

    public void clearKingsCheck() {
        kingBlack.setInCheck(false);
        kingBlack.clearAttackingPieces();
        kingWhite.setInCheck(false);
        kingWhite.clearAttackingPieces();
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

    public void clearDangerSquares() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j].setDanger(SquareDanger.PEACEFUL);
            }
        }
    }

    private void setAttackedSquares(ListIterator<Coordinates> attackPos, Color origin) {
        while (attackPos.hasNext()) {
            Coordinates currPos = attackPos.next();
            Square currSq = getSquare(currPos);
            if (currSq.getDanger() == SquareDanger.PEACEFUL) {
                currSq.setDanger((origin == Color.BLACK) ? SquareDanger.BLACK_ATTACKING : SquareDanger.WHITE_ATTACKING);
            } else if ((currSq.getDanger() == SquareDanger.BLACK_ATTACKING && origin == Color.WHITE) || (currSq.getDanger() == SquareDanger.WHITE_ATTACKING && origin == Color.BLACK)) {
                currSq.setDanger(SquareDanger.BOTH_ATTACKING);
            }
        }
    }

    private void updateDangerSquares(Color color) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Piece p = grid[i][j].getPiece();
                if (p != null && p.getColor() == color) {
                    setAttackedSquares(p.getAttackingPositions(), p.getColor());
                }
            }
        }
    }

    public void calculateAllPieces(Color firstColorToProcess, MoveHistory history) {
        calculateColorPieces(firstColorToProcess, history);
        updateDangerSquares(firstColorToProcess);
        calculateColorPieces((firstColorToProcess == Color.BLACK) ? Color.WHITE : Color.BLACK, history);
        updateDangerSquares((firstColorToProcess == Color.BLACK) ? Color.WHITE : Color.BLACK);
    }

    private void calculateColorPieces(Color currentColor, MoveHistory history) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Piece p = grid[y][x].getPiece();
                if (p != null && p.getColor() == currentColor) {
                    if (p instanceof Pawn) {
                        ((Pawn) p).updateAllPositions(new Coordinates(x, y), this, history);
                    } else {
                        p.updateAllPositions(new Coordinates(x, y), this);
                    }
                }
            }
        }
    }

    public int countAvailableMoves(Color color) {
        int moveCount = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Piece p = grid[y][x].getPiece();
                if (p != null && p.getColor() == color) {
                    moveCount += p.legalPositionsCount();
                }
            }
        }
        return moveCount;
    }

    public void clearPieceKingProtectors() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Piece p = grid[y][x].getPiece();
                if (p != null) {
                    p.setRoyalProtector(false);
                    p.clearKingProtectorCausingPiece();
                }
            }
        }
    }
}
