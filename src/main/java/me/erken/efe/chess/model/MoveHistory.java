package me.erken.efe.chess.model;

import java.util.Stack;

public class MoveHistory {
    private final Stack<Move> past;
    private final Stack<Move> future;

    public MoveHistory() {
        past = new Stack<>();
        future = new Stack<>();
    }

    public void doMove(Move move, Board board) throws IllegalMoveException {
        move.doMove(board);
        past.push(move);
        future.clear();
    }

    public void redoMove(Board board) throws IllegalMoveException {
        if (!future.isEmpty()) {
            Move m = future.pop();
            m.doMove(board);
            past.push(m);
        }
    }

    public void undoMove() {
        if (!past.isEmpty()) {
            Move m = past.pop();
            if (m instanceof RegularMove) {
                ((RegularMove) m).undoMove(past);
            } else {
                m.undoMove();
            }
            future.push(m);
        }
    }

    public Move lastMove() {
        // can be made so that it respects encapsulation (clone() ?)
        return past.peek();
    }

    public int moveCount() {
        return past.size();
    }

    public boolean isEmptyPast() {
        return past.isEmpty();
    }

    public boolean isEmptyFuture() {
        return future.isEmpty();
    }
}
