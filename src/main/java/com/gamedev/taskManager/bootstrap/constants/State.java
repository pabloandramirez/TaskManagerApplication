package com.gamedev.taskManager.bootstrap.constants;

import lombok.Getter;

@Getter
public enum State {
    PENDING ("Pending"),
    IN_PROGRESS ("In progress"),
    COMPLETED ("Completed");

    private final String state;

    State(String state){ this.state = state;}

}
