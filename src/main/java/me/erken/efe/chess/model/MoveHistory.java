package me.erken.efe.chess.model;

import java.util.Stack;

public class MoveHistory {
    private Stack<Move> history;

    public MoveHistory() {
        history = new Stack<Move>();
    }

    public void addMove(Move move) {
        history.push(move);
    }

    public Move getRemoveMove() {
        return history.pop();
    }

    public Move lastMove() {
        return history.peek();
    }

    public int moveCount() {
        return history.size();
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }
}