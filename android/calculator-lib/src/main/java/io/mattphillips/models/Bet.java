package io.mattphillips.models;

import java.math.BigDecimal;

import io.mattphillips.models.microtypes.Handicap;
import io.mattphillips.models.microtypes.Odds;
import io.mattphillips.models.microtypes.Stake;

public class Bet {

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
