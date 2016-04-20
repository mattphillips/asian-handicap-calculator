package io.mattphillips.models;

import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Odds;
import io.mattphillips.models.microtypes.Stake;

public class Bet {

    private static final String FULL_GOAL_REMAINDER = "0.00";
    private static final String HALF_GOAL_REMAINDER = "0.50";
    private static final String QUARTER_GOAL_LOWER_REMAINDER = "0.25";
    private static final String QUARTER_GOAL_UPPER_REMAINDER = "0.75";

    private final Team team;
    private final Odds odds;
    private final Handicap handicap;
    private final Stake stake;
    private final Score scoreline;

    public Bet(
            final Team team,
            final Odds odds,
            final Handicap handicap,
            final Stake stake,
            final Score scoreline) {
        this.team = team;
        this.odds = odds;
        this.handicap = handicap;
        this.stake = stake;
        this.scoreline = scoreline;
    }

    public Bet adjustScore(final int home, final int away) {
        return new Bet(
                this.getTeam(),
                this.getOdds(),
                this.getHandicap(),
                this.getStake(),
                new Score(home, away)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bet bet = (Bet) o;

        return !(scoreline != null ? !scoreline.equals(bet.scoreline) : bet.scoreline != null);

    }

    @Override
    public int hashCode() {
        return scoreline != null ? scoreline.hashCode() : 0;
    }

    public Team getTeam() {
        return team;
    }

    public Odds getOdds() {
        return odds;
    }

    public Handicap getHandicap() {
        return handicap;
    }

    public Stake getStake() {
        return stake;
    }

    public Score getScoreline() {
        return scoreline;
    }

    public boolean isFullGoalBet() {
        return getHandicapRemainder().equals(FULL_GOAL_REMAINDER);
    }

    public boolean isHalfGoalBet() {
        return getHandicapRemainder().equals(HALF_GOAL_REMAINDER);
    }

    public boolean isQuarterGoalBet() {
        return getHandicapRemainder().equals(QUARTER_GOAL_LOWER_REMAINDER)
                || getHandicapRemainder().equals((QUARTER_GOAL_UPPER_REMAINDER));
    }

    private String getHandicapRemainder() {
        return handicap.getRemainder().abs().toString();
    }

    @Override
    public String toString() {
        return "{Team = " + team
                + ", Odds = " + odds
                + ", Handicap = " + handicap
                + ", Stake = " + stake
                + ", Score = " + scoreline
                +"}";
    }
}
