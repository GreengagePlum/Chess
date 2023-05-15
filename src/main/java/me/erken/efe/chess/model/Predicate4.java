package me.erken.efe.chess.model;

@FunctionalInterface
interface Predicate4<T, U, V, Y> {
    boolean accept(T a, U b, V c, Y d);
}
