package com.agileengine.task;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer <T, U, Z> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @param z the third input argument
     */
    void accept(T t, U u, Z z);


    default TriConsumer<T, U, Z> andThen(TriConsumer<? super T, ? super U, ? super Z> after) {
        Objects.requireNonNull(after);

        return (l, r, z) -> {
            accept(l, r, z);
            after.accept(l, r, z);
        };
    }
}