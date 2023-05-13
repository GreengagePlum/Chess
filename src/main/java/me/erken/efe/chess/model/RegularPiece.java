package me.erken.efe.chess.model;

public abstract non-sealed class RegularPiece extends Piece {

    public RegularPiece(Color color) {
        super(color);
    }

    protected abstract void setKingProtectorsInPath(Coordinates sourceCoords, Board board);

    protected void setOppositeKingToCheck(Board board) {
        Color oppositeColor = (this.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK;
        if (legalPositions.contains(board.findPiece(board.getKing(oppositeColor)))) {
            board.getKing(oppositeColor).setInCheck(true);
            board.getKing(oppositeColor).addAttackingPiece(this);
        }
    }

}
