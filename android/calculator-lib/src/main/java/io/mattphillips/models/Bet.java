package io.mattphillips.models;

import java.math.BigDecimal;

public class Bet {

    private final Team team;
    private final BigDecimal odds;
    private final BigDecimal handicap;
    private final BigDecimal stake;
    private final Score scoreline;

    public Bet(
            final Team team,
            final BigDecimal odds,
            final BigDecimal handicap,
            final BigDecimal stake,
            final Score scoreline) {
        this.team = team;
        this.odds = odds;
        this.handicap = handicap;
        this.stake = stake;
        this.scoreline = scoreline;
    }

    public Team getTeam() {
        return team;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public BigDecimal getHandicap() {
        return handicap;
    }

    public BigDecimal getStake() {
        return stake;
    }

    public Score getScoreline() {
        return scoreline;
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
