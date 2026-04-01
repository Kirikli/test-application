package com.example.test_application.model;

public enum TaskStatus {
    NEW,
    IN_PROGRESS,
    DONE,
    CANCELLED;

    public boolean canUpdateStatus(TaskStatus target) {
        return switch (this) {
            case NEW -> target == IN_PROGRESS || target == CANCELLED;
            case IN_PROGRESS -> target == DONE || target == CANCELLED;
            case DONE, CANCELLED -> false;
        };
    }
}
