package me.erken.efe.chess.model;

public class PawnPromotionException extends Exception {
    public PawnPromotionException() {
        super("Besoin de promouvoir un pion");
    }
}
