package me.erken.efe.chess.model;

public class Game extends Board {
    private final Board board;
    private final Player player1, player2;
    private final MoveHistory history;
    private Player currentPlayer; // current turn indicator
    private GameEndCause gameEndCause;

    public Game() {
        board = new Board();
        player1 = new Player(Color.WHITE);
        player2 = new Player(Color.BLACK);
        currentPlayer = player1;
        history = new MoveHistory();
        gameEndCause = GameEndCause.NONE;
        board.calculateAllPieces((currentPlayer.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK);
    }

    public SquareState getSquareState(int x, int y) {
        return board.getSquare(new Coordinates(x, y)).getState();
    }

    public PieceType getPieceType(int x, int y) {
        Piece p = board.getSquare(new Coordinates(x, y)).getPiece();
        PieceType result;
        if (p instanceof King) {
            result = (p.getColor() == Color.BLACK) ? PieceType.BLACK_KING : PieceType.WHITE_KING;
        } else if (p instanceof Queen) {
            result = (p.getColor() == Color.BLACK) ? PieceType.BLACK_QUEEN : PieceType.WHITE_QUEEN;
        } else if (p instanceof Rook) {
            result = (p.getColor() == Color.BLACK) ? PieceType.BLACK_ROOK : PieceType.WHITE_ROOK;
        } else if (p instanceof Bishop) {
            result = (p.getColor() == Color.BLACK) ? PieceType.BLACK_BISHOP : PieceType.WHITE_BISHOP;
        } else if (p instanceof Knight) {
            result = (p.getColor() == Color.BLACK) ? PieceType.BLACK_KNIGHT : PieceType.WHITE_KNIGHT;
        } else if (p instanceof Pawn) {
            result = (p.getColor() == Color.BLACK) ? PieceType.BLACK_PAWN : PieceType.WHITE_PAWN;
        } else {
            result = PieceType.NONE;
        }
        return result;
    }

    public Color getPieceColor(int x, int y) {
        return board.getSquare(new Coordinates(x, y)).getPiece().getColor();
    }

    public Color getCurrentPlayerColor() {
        return currentPlayer.getColor();
    }

    public boolean isEnded() {
        return gameEndCause != GameEndCause.NONE;
    }

    public GameEndCause endReason() {
        return gameEndCause;
    }

    public boolean selectionExists() {
        return currentPlayer.getSelection() != null;
    }

    public int selectionX() {
        return board.findSquare(currentPlayer.getSelection()).x;
    }

    public int selectionY() {
        return board.findSquare(currentPlayer.getSelection()).y;
    }

    public void makeSelection(int x, int y) throws EndOfGameException {
        if (isEnded()) {
            throw new EndOfGameException();
        }
        currentPlayer.makeSelection(board.getSquare(new Coordinates(x, y)), board);
    }

    public void makeMove(int x, int y) throws EndOfGameException, IllegalMoveException {
        if (isEnded()) {
            throw new EndOfGameException();
        }
        currentPlayer.makeMove(board.getSquare(new Coordinates(x, y)), history, board);
        postMoveSequence();
    }

    private void postMoveSequence() {
        board.clearDangerSquares();
        board.clearKingsCheck();
        board.clearPieceKingProtectors();
        board.calculateAllPieces(currentPlayer.getColor());
        advanceTurn(); //?? maybe at the end (not the right place/order here)
        updateIsEndGame();
    }

    private void advanceTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void updateIsEndGame() {
        boolean noViableMoves = board.countAvailableMoves(currentPlayer.getColor()) == 0;
        if (board.getKing(currentPlayer.getColor()).isInCheck()) {
            gameEndCause = (noViableMoves) ? GameEndCause.CHECKMATE : GameEndCause.NONE;
        } else {
            gameEndCause = (noViableMoves) ? GameEndCause.STALEMATE : GameEndCause.NONE;
        }
    }
}
