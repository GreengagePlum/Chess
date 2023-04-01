package me.erken.efe.chess.model;

public abstract class Piece {
    private final Color color;
    private int positionX, positionY;

    public Piece(Color color, int positionX, int positionY) {
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Color getColor() {
        return color;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setPosition(int positionX, int positionY) {
        setPositionX(positionX);
        setPositionY(positionY);
    }

    @Override
    public String toString() {
        return "Pièce aux coordonnées : (%d, %d)".formatted(positionX, positionY);
    }

    protected abstract boolean isValidMove(int positionX, int positionY, Board board);
}
