package com.devsteady.onyx.types;

import lombok.Getter;
import lombok.Setter;

public class Tuple<T, F> implements Cloneable{

    public static <T, F>Tuple of(T left, F right) {
        return new Tuple<T,F>(left,right);
    }

    @Getter
    @Setter
    private T left;

    @Getter
    @Setter
    private F right;

    //todo type inference based on class passed.

    public Tuple(T left, F right) {
        this.left = left;
        this.right = right;
    }

    public Tuple(Tuple<T, F> tuple) {
        this.left = tuple.getLeft();
        this.right = tuple.getRight();
    }

    public Tuple<T,F> clone(Tuple<T, F> clone) {
        return new Tuple<>(clone);
    }
}
