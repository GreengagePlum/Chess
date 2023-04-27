package me.erken.efe.chess.view;

import javafx.scene.paint.Color;
import me.erken.efe.chess.model.GameEndCause;
import me.erken.efe.chess.model.PieceType;
import me.erken.efe.chess.model.SquareState;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static class SpriteMap {
        private static final String spriteDir = "View/Sprites/";
        private static final Map<PieceType, String> pairs;

        static {
            pairs = new HashMap<>();
            pairs.put(PieceType.BLACK_KING, spriteDir + "Black_King.png");
            pairs.put(PieceType.BLACK_QUEEN, spriteDir + "Black_Queen.png");
            pairs.put(PieceType.BLACK_ROOK, spriteDir + "Black_Rook.png");
            pairs.put(PieceType.BLACK_BISHOP, spriteDir + "Black_Bishop.png");
            pairs.put(PieceType.BLACK_KNIGHT, spriteDir + "Black_Knight.png");
            pairs.put(PieceType.BLACK_PAWN, spriteDir + "Black_Pawn.png");
            pairs.put(PieceType.WHITE_KING, spriteDir + "White_King.png");
            pairs.put(PieceType.WHITE_QUEEN, spriteDir + "White_Queen.png");
            pairs.put(PieceType.WHITE_ROOK, spriteDir + "White_Rook.png");
            pairs.put(PieceType.WHITE_BISHOP, spriteDir + "White_Bishop.png");
            pairs.put(PieceType.WHITE_KNIGHT, spriteDir + "White_Knight.png");
            pairs.put(PieceType.WHITE_PAWN, spriteDir + "White_Pawn.png");
            pairs.put(PieceType.NONE, null);
        }

        public static String getSprite(PieceType pieceType) {
            return pairs.get(pieceType);
        }
    }

    public static class ColorPalette {
        public static final Color RED = Color.rgb(233, 114, 103);
        public static final Color BLUE = Color.rgb(104, 174, 221);
        public static final Color GREEN = Color.rgb(141, 205, 111);
        public static final Color BLACK = Color.rgb(0, 0, 0);
        public static final Color WHITE = Color.rgb(255, 255, 255);

        private static final Map<SquareState, Color> pairs;

        static {
            pairs = new HashMap<>();
            pairs.put(SquareState.SELECTED, BLUE);
            pairs.put(SquareState.HIGHLIGHTED, GREEN);
            pairs.put(SquareState.NORMAL, BLACK);
        }

        public static Color getColor(SquareState squareState) {
            return pairs.get(squareState);
        }

        public static double getOpacity(SquareState squareState) {
            return (squareState == SquareState.NORMAL) ? 0d : 1d;
        }
    }

    public static class EndGameExplanations {
        private static final Map<GameEndCause, String> pairs;

        static {
            pairs = new HashMap<>();
            pairs.put(GameEndCause.CHECKMATE, "Échec et mat");
            pairs.put(GameEndCause.STALEMATE, "Pat");
            pairs.put(GameEndCause.NONE, "");
        }

        public static String getExplanation(GameEndCause endCause) {
            return pairs.get(endCause);
        }

    }

}
