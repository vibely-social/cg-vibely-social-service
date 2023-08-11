package com.vibely_social.converter;

import java.util.List;

public interface Converter<S, T> {
    T convert(S source);
    S revert (T target);
    List<T> convert(List<S> sources);
    List<S> revert(List<T> targets);
}
