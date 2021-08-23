package com.fornite.apimeter.helper;

@FunctionalInterface
public interface HeaderSet<T, R> {
    R setHeader(T t) throws Exception;
}
