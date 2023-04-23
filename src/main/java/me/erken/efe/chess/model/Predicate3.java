package me.erken.efe.chess.model;

@FunctionalInterface
interface Predicate3<T, U, V> {
    boolean accept(T a, U b, V c);
}
