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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        if (home != score.home) return false;
        return away == score.away;

    }

    @Override
    public int hashCode() {
        int result = home;
        result = 31 * result + away;
        return result;
    }

    @Override
    public String toString() {
        return "{ Home = " + home + ", Away = " + away + "}";
    }

}