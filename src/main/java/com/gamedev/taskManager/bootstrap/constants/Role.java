package com.gamedev.taskManager.bootstrap.constants;

import lombok.Getter;

@Getter
public enum Role {
    DEV ("Developer"),
    QA ("Quality"),
    DEV_OPS ("DEV OPS");

    private final String role;

    Role(String role){ this.role = role;}

}
