package me.erken.efe.chess.model;

public class EndOfGameException extends Exception {
    public EndOfGameException() {
        super("Fin du jeu");
    }
}
