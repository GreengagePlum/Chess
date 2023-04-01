package me.erken.efe.chess;

import me.erken.efe.chess.model.Board;
import me.erken.efe.chess.model.Color;
import me.erken.efe.chess.model.Coordinates;
import me.erken.efe.chess.model.Piece;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        for (int y = 0; y < Board.HEIGHT; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                Piece p = board.getSquare(new Coordinates(x, y)).getPiece();
                try {
                    if (p.getColor() == Color.BLACK) {
                        System.out.print("b ");
                    } else if (p.getColor() == Color.WHITE) {
                        System.out.print("w ");
                    }
                } catch (NullPointerException np) {
                }
            }
        }
        System.out.println();
    }
}
