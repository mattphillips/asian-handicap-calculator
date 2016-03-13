package io.mattphillips.models;

public class Score {

    private final int home;
    private final int away;

    public Score(final int home, final int away) {
        this.home = home;
        this.away = away;
    }

    public int homeScore() {
        return home;
    }

    public int awayScore() {
        return away;
    }

    @Override
    public String toString() {
        return "{ Home = " + home + ", Away = " + away + "}";
    }

}