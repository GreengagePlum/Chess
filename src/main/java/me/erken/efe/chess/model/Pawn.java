package me.erken.efe.chess.model;

public final class Pawn extends Piece {

    private boolean firstMove;

    public Pawn(Color color, int positionX, int positionY) {
        super(color, positionX, positionY);
        firstMove = true;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void consumeFirstMove() {
        firstMove = false;
    }

    @Override
    protected boolean isValidMove(int positionX, int positionY, Board board) {
        if (this.getPositionY() <= positionY || Math.abs(this.getPositionX() - positionX) >= 2) {
            return false;
        }
        if (this.getPositionX() == positionX && positionY >= this.getPositionY() - ((firstMove) ? 2 : 1)) {
            for (int y = this.getPositionY() - 1; y >= positionY; y--) {
                if (board.getSquare(this.getPositionX(), y).getPiece() != null) {
                    return false;
                }
            }
        } else if (Math.abs(this.getPositionX() - positionX) == 1 && Math.abs(this.getPositionY() - positionY) == 1) {
            if (board.getSquare(positionX, positionY).getPiece() == null) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
