package com.hometask.task_security.model;

import lombok.Getter;

public enum Authorization {
    EDIT("edit"),
    READ("read"),
    DELETE("delete");


    @Getter
    private final String authorization;

    Authorization(String authorization) {
        this.authorization = authorization;
    }
}