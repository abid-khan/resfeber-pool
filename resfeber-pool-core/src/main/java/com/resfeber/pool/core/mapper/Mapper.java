package com.resfeber.pool.core.mapper;

import java.text.ParseException;

public interface Mapper<S, T> {
    T fromSource(S s);

    S toSource(T t) throws ParseException;
}
