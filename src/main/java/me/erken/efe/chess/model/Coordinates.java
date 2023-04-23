package me.erken.efe.chess.model;

public class Coordinates {
    public int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coordinates && x == ((Coordinates) obj).x && y == ((Coordinates) obj).y;
    }
}
