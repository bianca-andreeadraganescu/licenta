package com.madmin.policies.repository;

public interface UserRepositoryFunction<T, R> {
    R apply(T t);
}
