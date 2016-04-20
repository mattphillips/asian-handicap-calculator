package io.mattphillips.models;

public enum Team {
    HOME,
    AWAY;

    public boolean isHome() {
        return this == HOME;
    }

    public boolean isAway() {
        return this == AWAY;
    }
}
