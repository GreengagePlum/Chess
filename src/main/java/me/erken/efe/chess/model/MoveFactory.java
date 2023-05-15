package me.erken.efe.chess.model;

import java.util.List;

public class MoveFactory {
    private static Square getCastlingMoveRookSource(Coordinates sourceCoordsKing, Coordinates destinationCoordsKing, Board board) {
        // lightweight version of the King's castlingCheck(), can be optimized, simplified (repetitive so far)
        List<Piece> rooks = board.getPieces(Rook.class, board.getSquare(sourceCoordsKing).getPiece().getColor());
        if (destinationCoordsKing.x - sourceCoordsKing.x == -2) {
            for (Piece r :
                    rooks) {
                Coordinates rookCoords = board.findPiece(r);
                if (rookCoords.x - sourceCoordsKing.x < 0) {
                    return board.getSquare(rookCoords);
                }
            }
        } else if (destinationCoordsKing.x - sourceCoordsKing.x == 2) {
            for (Piece r :
                    rooks) {
                Coordinates rookCoords = board.findPiece(r);
                if (rookCoords.x - sourceCoordsKing.x > 0) {
                    return board.getSquare(rookCoords);
                }
            }
        }
        return null;
    }

    private static Square getEnPassantVictim(Coordinates source, Coordinates destination, Board board) {
        return board.getSquare(new Coordinates(source.x + (destination.x - source.x), source.y));
    }

    public static Move create(Player player, Square source, Square destination, Board board, String rank) throws PawnPromotionException {
        Coordinates sourceCoords = board.findSquare(source);
        Coordinates destCoords = board.findSquare(destination);
        if (source.getPiece() instanceof King && Math.abs(destCoords.x - sourceCoords.x) == 2) {
            Square rookSource = getCastlingMoveRookSource(sourceCoords, destCoords, board);
            return new CastlingMove(player, source, destination, rookSource, board.getSquare(new Coordinates(sourceCoords.x + ((destCoords.x - sourceCoords.x < 0) ? -1 : 1), sourceCoords.y)));
        } else if (source.getPiece() instanceof Pawn && destination.getPiece() == null && Math.abs(destCoords.x - sourceCoords.x) == 1) {
            return new EnPassantMove(player, source, destination, getEnPassantVictim(sourceCoords, destCoords, board));
        } else if (source.getPiece() instanceof Pawn && destCoords.y == ((source.getPiece().getColor() == Color.BLACK) ? 7 : 0)) {
            return new PawnPromotionMove(player, source, destination, rank);
        }
        return new RegularMove(player, source, destination);
    }
}
