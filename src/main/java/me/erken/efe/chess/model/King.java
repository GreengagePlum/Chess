package me.erken.efe.chess.model;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class King extends Piece {

    private boolean inCheck;
    private final List<Piece> attackingPieces;

    public King(Color color) {
        super(color);
        inCheck = false;
        attackingPieces = new LinkedList<>();
    }

    public boolean isInCheck() {
        return inCheck;
    }

    public void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }

    public void addAttackingPiece(Piece piece) {
        attackingPieces.add(piece);
    }

    public void clearAttackingPieces() {
        attackingPieces.clear();
    }

    public ListIterator<Piece> getAttackingPieces() {
        return attackingPieces.listIterator();
    }

    private boolean pathCheck(Coordinates sourceCoords, Coordinates destinationCoords) {
        return Math.abs(destinationCoords.x - sourceCoords.x) == 1 && Math.abs(destinationCoords.y - sourceCoords.y) == 1;
    }

    private boolean destinationPieceCheck(Coordinates destinationCoords, Board board) {
        Piece destPiece = board.getSquare(destinationCoords).getPiece();
        return destPiece == null || destPiece.getColor() != this.getColor();
    }

    private boolean dangerCheck(Coordinates destinationCoords, Board board) {
        SquareDanger sqd = board.getSquare(destinationCoords).getDanger();
        if (sqd == SquareDanger.PEACEFUL) {
            return true;
        }
        if (sqd == SquareDanger.BOTH_ATTACKING) {
            return false;
        }
        return sqd == ((getColor() == Color.BLACK) ? SquareDanger.BLACK_ATTACKING : SquareDanger.WHITE_ATTACKING);
    }

    @Override
    protected boolean isLegalPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return coordinateCheck(destinationCoords)
                && dangerCheck(destinationCoords, board)
                && destinationPieceCheck(destinationCoords, board)
                && pathCheck(sourceCoords, destinationCoords);
    }

    @Override
    protected boolean isAttackingPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return coordinateCheck(destinationCoords)
                && pathCheck(sourceCoords, destinationCoords);
    }

    private List<Coordinates> traversePath(Coordinates sourceCoords, Board board, Predicate3<Coordinates, Coordinates, Board> checker) {
        List<Coordinates> result = new LinkedList<>();
        int x = sourceCoords.x - 1;
        int y = sourceCoords.y - 1;
        for (int i = 0; i < 3; i++) {
            if (checker.accept(sourceCoords, new Coordinates(x, y), board)) {
                result.add(new Coordinates(x, y));
            }
            y++;
        }
        x = sourceCoords.x + 1;
        y = sourceCoords.y - 1;
        for (int i = 0; i < 3; i++) {
            if (checker.accept(sourceCoords, new Coordinates(x, y), board)) {
                result.add(new Coordinates(x, y));
            }
            y++;
        }
        x = sourceCoords.x;
        y = sourceCoords.y - 1;
        if (checker.accept(sourceCoords, new Coordinates(x, y), board)) {
            result.add(new Coordinates(x, y));
        }
        y = sourceCoords.y + 1;
        if (checker.accept(sourceCoords, new Coordinates(x, y), board)) {
            result.add(new Coordinates(x, y));
        }
        return result;
    }

    @Override
    public void updateLegalPositions(Coordinates sourceCoords, Board board) {
        legalPositions.clear();
        legalPositions.addAll(traversePath(sourceCoords, board, this::isLegalPosition));
    }

    @Override
    public void updateAttackingPositions(Coordinates sourceCoords, Board board) {
        attackingPositions.clear();
        attackingPositions.addAll(traversePath(sourceCoords, board, this::isAttackingPosition));
    }

    @Override
    protected void setKingProtectorsInPath(Coordinates sourceCoords, Board board) {
    }

    @Override
    protected void setOppositeKingToCheck(Board board) {
    }

    @Override
    protected boolean posInPathLeadingToKing(Coordinates sourceCoords, Coordinates toTest, Board board) {
        return false;
    }
}
