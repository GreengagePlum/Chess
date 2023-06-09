package me.erken.efe.chess.model;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class King extends RoyalPiece {

    private boolean moved;

    public King(Color color) {
        super(color);
        moved = false;
    }

    public boolean hasMoved() {
        return moved;
    }

    void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    protected boolean pathCheck(Coordinates sourceCoords, Coordinates destinationCoords) {
        return !sourceCoords.equals(destinationCoords) && Math.abs(destinationCoords.x - sourceCoords.x) <= 1 && Math.abs(destinationCoords.y - sourceCoords.y) <= 1;
    }

    @Override
    protected boolean obstructionCheck(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return true;
    }

    private boolean dangerCheck(Coordinates destinationCoords, Board board) {
        SquareDanger sqd = board.getSquare(destinationCoords).getDanger();
        if (sqd == SquareDanger.PEACEFUL) {
            return attackingPiecePathCheck(destinationCoords, board);
        }
        if (sqd == SquareDanger.BOTH_ATTACKING) {
            return false;
        }
        return (sqd == ((getColor() == Color.BLACK) ? SquareDanger.BLACK_ATTACKING : SquareDanger.WHITE_ATTACKING)) && attackingPiecePathCheck(destinationCoords, board);
    }

    private boolean attackingPiecePathCheck(Coordinates destinationCoords, Board board) {
        Square sq = board.getSquare(board.findPiece(this));
        sq.setPiece(null);
        for (ListIterator<Piece> it = getAttackingPieces(); it.hasNext(); ) {
            Piece p = it.next();
            Coordinates pos = board.findPiece(p);
            if (p.pathCheck(pos, destinationCoords) && p.obstructionCheck(pos, destinationCoords, board)) {
                sq.setPiece(this);
                return false;
            }
        }
        sq.setPiece(this);
        return true;
    }

    private boolean castlingCheck(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        if (destinationCoords.y == sourceCoords.y && !hasMoved() && !isInCheck()) {
            List<Piece> rooks = board.getPieces(Rook.class, getColor());
            rooks.removeIf(r -> ((Rook) r).hasMoved());
            if (destinationCoords.x - sourceCoords.x == -2) {
                for (Piece r :
                        rooks) {
                    Coordinates rookCoords = board.findPiece(r);
                    if (rookCoords.x - sourceCoords.x < 0) {
                        Coordinates tmp = new Coordinates(sourceCoords.x - 1, sourceCoords.y);
                        for (; !tmp.equals(rookCoords); tmp.x--) {
                            if (board.getSquare(tmp).getPiece() != null || !dangerCheck(tmp, board)) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            } else if (destinationCoords.x - sourceCoords.x == 2) {
                for (Piece r :
                        rooks) {
                    Coordinates rookCoords = board.findPiece(r);
                    if (rookCoords.x - sourceCoords.x > 0) {
                        Coordinates tmp = new Coordinates(sourceCoords.x + 1, sourceCoords.y);
                        for (; !tmp.equals(rookCoords); tmp.x++) {
                            if (board.getSquare(tmp).getPiece() != null || !dangerCheck(tmp, board)) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected boolean isLegalPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return coordinateCheck(destinationCoords)
                && dangerCheck(destinationCoords, board)
                && destinationPieceCheck(destinationCoords, board)
                && (pathCheck(sourceCoords, destinationCoords)
                || castlingCheck(sourceCoords, destinationCoords, board));
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
        x = sourceCoords.x - 2;
        y = sourceCoords.y;
        if (checker.accept(sourceCoords, new Coordinates(x, y), board)) {
            result.add(new Coordinates(x, y));
        }
        x = sourceCoords.x + 2;
        if (checker.accept(sourceCoords, new Coordinates(x, y), board)) {
            result.add(new Coordinates(x, y));
        }
        return result;
    }

    @Override
    protected void updateLegalPositions(Coordinates sourceCoords, Board board) {
        legalPositions.clear();
        legalPositions.addAll(traversePath(sourceCoords, board, this::isLegalPosition));
    }

    @Override
    protected void updateAttackingPositions(Coordinates sourceCoords, Board board) {
        attackingPositions.clear();
        attackingPositions.addAll(traversePath(sourceCoords, board, this::isAttackingPosition));
    }

    @Override
    protected boolean posInPathLeadingToKing(Coordinates sourceCoords, Coordinates toTest, Board board) {
        return false;
    }
}
