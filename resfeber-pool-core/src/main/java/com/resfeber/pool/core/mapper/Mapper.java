package com.resfeber.pool.core.mapper;

public interface Mapper<S, T> {
    T fromSource(S s);

    S toSource(T t);
}
