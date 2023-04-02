package me.erken.efe.chess.model;

public class IllegalMoveException extends Exception {
    public IllegalMoveException() {
        super("Coup illégal");
    }
}
